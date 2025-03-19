package com.project_app.project_management.task;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.project.Project;
import com.project_app.project_management.project.ProjectRepository;
import com.project_app.project_management.project.ProjectUsers;
import com.project_app.project_management.project.ProjectUsersRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    final  TaskRepository taskRepository;
    final ProjectRepository projectRepository;
    final UserRepository userRepository;
    final ProjectUsersRepository projectUsersRepository;
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository, ProjectUsersRepository projectUsersRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectUsersRepository = projectUsersRepository;
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
         task.setAssignedUser(userRepository.findById(taskDto.getAssignedTo()));
         Optional<Project> projectOptional = projectRepository.findById(project_id);
          task.setProject(projectOptional.orElse(null));
         return  taskRepository.save(task);
    }
    public List<Task> filterTasks(String status, String priority, Date deadline, User user ,int size , int page) {
        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user) ;
        List<Integer> projectIds = projectUsers.stream()
                .map(ProjectUsers::getId)
                .toList();
        Specification<Task> spec = Specification.where(null);

        if (status != null && !status.isEmpty()) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }
        if (priority != null && !priority.isEmpty()) {
            spec = spec.and(TaskSpecification.hasPriority(priority));
        }
        if (deadline != null) {
            spec = spec.and(TaskSpecification.hasDeadline(deadline));
        }
            spec = spec.and(TaskSpecification.hasProjectIds(projectIds));

        return taskRepository.findAllBy(spec ,Pageable.ofSize(size).withPage(page));
    }

    public  List<Task> searchTasks(User user , int page , int size ,String taskName) {
        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user) ;
        List<Integer> projectIds = projectUsers.stream()
                .map(ProjectUsers::getId)
                .toList();
        return taskRepository.findAllByProjectIdInAndTitleStartingWith(projectIds ,taskName, Pageable.ofSize(size).withPage(page));



    }
    public Task updateTask(TaskDto taskDto , int project_id ) {
        Task task  = taskRepository.findById(project_id) ;
        task.setDeadline(taskDto.getDeadline());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setStatus(taskDto.getStatus());
        task.setTitle(taskDto.getName());
        task.setAssignedUser(userRepository.findById(taskDto.getAssignedTo()));
        return  taskRepository.save(task);
    }


}
