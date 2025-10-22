package com.project_app.project_management.task;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.project.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUsersRepository projectUsersRepository;
    private final ProjectService projectService;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       UserRepository userRepository,
                       ProjectUsersRepository projectUsersRepository,
                       ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectUsersRepository = projectUsersRepository;
        this.projectService = projectService;
    }

    public List<Task> getProjectTasks(int projectId, int page, int size) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with ID: " + projectId);
        }
        return taskRepository.findAllByProject_Id(projectId, Pageable.ofSize(size).withPage(page));
    }

    public List<Task> getAllMyTasks(User user, String status) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        if (status == null || status.isEmpty()) {
            return taskRepository.findAllByAssignedUser(user);
        }

        return taskRepository.findAllByAssignedUser(user) ;
    }

    public Task getTaskById(int taskId) {
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with ID: " + taskId);
        }
        return task;
    }

    public Task createTask(TaskDto taskDto, int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with ID: " + projectId));

        User assignedUser = userRepository.findById(taskDto.getAssignedTo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found with ID: " + taskDto.getAssignedTo()));

        Task task = new Task();
        task.setDeadline(taskDto.getDeadline());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus("To-Do");
        task.setTitle(taskDto.getName());
        task.setAssignedUser(assignedUser);
        task.setProject(project);

        return taskRepository.save(task);
    }

    public List<Task> filterTasks(String status, String priority, Date deadline, User user, int size, int page) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user);
        List<Integer> projectIds = projectUsers.stream()
                .map(pu -> pu.getProject().getId())
                .toList();

        String deadlineStr = null;
        if (deadline != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            deadlineStr = sdf.format(deadline);
        }

        return taskRepository.findFilteredTasks(
                status,
                priority,
                deadlineStr,
                projectIds,
                Pageable.ofSize(size).withPage(page)
        );
    }

    public List<Task> searchTasks(User user, int page, int size, String taskName) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }

        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user);
        List<Integer> projectIds = projectUsers.stream()
                .map(pu -> pu.getProject().getId())
                .toList();

        return taskRepository.findAllByProjectIdInAndTitleStartingWith(projectIds, taskName, Pageable.ofSize(size).withPage(page));
    }

    public Task updateTask(TaskDto taskDto, int taskId) {
        Task task = taskRepository.findById(taskId);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found with ID: " + taskId);
        }

        User assignedUser = userRepository.findById(taskDto.getAssignedTo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assigned user not found with ID: " + taskDto.getAssignedTo()));

        task.setDeadline(taskDto.getDeadline());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setTitle(taskDto.getName());
        task.setAssignedUser(assignedUser);

        // Update project progress after task update
        projectService.updateProgress(task.getProject().getId());

        return taskRepository.save(task);
    }
}