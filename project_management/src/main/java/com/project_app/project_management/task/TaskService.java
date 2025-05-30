package com.project_app.project_management.task;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.project.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {
    final  TaskRepository taskRepository;
    final ProjectRepository projectRepository;
    final UserRepository userRepository;
    final ProjectUsersRepository projectUsersRepository;
    final ProjectService projectService;
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, ProjectUsersRepository projectUsersRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectUsersRepository = projectUsersRepository;
        this.projectService = projectService;
    }
    public List<Task> getProjectTasks(int project_id , int page , int size) {
     return  taskRepository.findAllByProject_Id(project_id , Pageable.ofSize(size).withPage(page)) ;
    }
    public List<Task> getAllMyTasks(User user ,String status) {
        if(status == null|| status.isEmpty()) {
             return  taskRepository.findAllByAssignedUser(user) ;
        }
         return taskRepository.findAllByAssignedUser(user);
    }
    public Task getTaskById(int task_id) {
          return taskRepository.findById(task_id);
    }

    public Task createTask(TaskDto taskDto , int project_id )   {
         Task task = new Task();
         task.setDeadline(taskDto.getDeadline());
         task.setDescription(taskDto.getDescription());
        // task.setAttachment(taskDto.getAttachment());
         task.setPriority(taskDto.getPriority());
         task.setStatus("To-Do");
         task.setTitle(taskDto.getName());
         task.setAssignedUser(userRepository.findById(taskDto.getAssignedTo()).get());
         Optional<Project> projectOptional = projectRepository.findById(project_id);
          task.setProject(projectOptional.orElse(null));
         return  taskRepository.save(task);
    }
    public List<Task> filterTasks(String status, String priority, Date deadline, User user ,int size , int page) {
        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user) ;
        List<Integer> projectIds = projectUsers.stream()
                .map(ProjectUsers::getId)
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

    public  List<Task> searchTasks(User user , int page , int size ,String taskName) {

        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user) ;
        List<Integer> projectIds = projectUsers.stream()
                .map(ProjectUsers::getId)
                .toList();
        return taskRepository.findAllByProjectIdInAndTitleStartingWith(projectIds ,taskName, Pageable.ofSize(size).withPage(page));

    }
    public Task updateTask(TaskDto taskDto , int task_id ) {
        Task task  = taskRepository.findById(task_id) ;
        task.setDeadline(taskDto.getDeadline());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setTitle(taskDto.getName());
        task.setAssignedUser(userRepository.findById(taskDto.getAssignedTo()).get());
        projectService.updateProgress(task.getProject().getId());

        return  taskRepository.save(task);
    }


}
