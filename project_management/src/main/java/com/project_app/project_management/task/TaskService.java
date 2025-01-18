package com.project_app.project_management.task;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.project.Project;
import com.project_app.project_management.project.ProjectRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    final  TaskRepository taskRepository;
    final ProjectRepository projectRepository;
    final UserRepository userRepository;
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
    public List<Task> getProjectTasks(int project_id , int page , int size) {
     return  taskRepository.findAllByProject_Id(project_id , Pageable.ofSize(size).withPage(page)) ;
    }
    public List<Task> getAllMyTasks(User user ,String status) {
        if(status == null|| status.isEmpty()) {
             return  taskRepository.findAllByAssignedUser(user) ;
        }
         return taskRepository.findAllByAssignedUserAndStatus(user , status);
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


}
