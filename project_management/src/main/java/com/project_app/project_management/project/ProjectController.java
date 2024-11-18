package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/project")
@RestController
public class ProjectController {
    final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/add_project")
    public ResponseEntity<Project> addProject(@RequestBody ProjectDTO project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(projectService.addProject(project ,currentUser)) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectById(id)) ;
    }
    @GetMapping("/my_projects")
    public ResponseEntity<List<Project>> getMyProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
       return ResponseEntity.ok(projectService.getAllProjects(currentUser));
    }
}
