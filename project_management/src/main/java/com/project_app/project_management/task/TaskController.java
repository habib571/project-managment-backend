package com.project_app.project_management.task;

import com.project_app.project_management.auth.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    final private TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping("/add-task/{project_id}")
    public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto , @PathVariable int project_id) {
        return ResponseEntity.ok(taskService.createTask(taskDto, project_id)) ;
    }
    @GetMapping("/my-tasks/")
    public ResponseEntity<List<Task>> getMyTasks(@RequestParam("status") String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(taskService.getAllMyTasks(currentUser ,status)) ;
    }

    @GetMapping ("/project-tasks/{project_id}")
    public ResponseEntity<List<Task>> getProjectTasks(@PathVariable int project_id, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(taskService.getProjectTasks(project_id, page, size));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Task>> filterTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) @DateTimeFormat (pattern = "dd-MM-yyyy") Date deadline  ,
            @RequestParam int page,
            @RequestParam int size
    )
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<Task> tasks = taskService.filterTasks(status, priority, deadline , currentUser ,size , page);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/search/{name}")
    public  ResponseEntity<List<Task>> searchTasks(@PathVariable String name , @RequestParam int page, @RequestParam int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<Task>  tasks = taskService.searchTasks(currentUser ,page ,size , name);
        return ResponseEntity.ok(tasks);

    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody TaskDto taskDto) {
        return  ResponseEntity.ok(taskService.updateTask(taskDto ,id)) ;
     }


}
