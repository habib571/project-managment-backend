package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequestMapping("/project")
@RestController
public class ProjectController {
    final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @PostMapping("/add_project")
    public ResponseEntity<Project> addProject(@RequestBody ProjectDTO project) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return  ResponseEntity.ok(projectService.addProject(project ,currentUser)) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectById(id)) ;
    }
    @GetMapping("/created_projects")
    public ResponseEntity<List<Project>> getCreatedProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
       return ResponseEntity.ok(projectService.getCreatedProjects(currentUser));
    }
    @GetMapping("/my_projects")
    public ResponseEntity<List<Project>> getMyProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(projectService.getAllMyProjects(currentUser));

    }
    @PostMapping("/add_member")
    public ResponseEntity<ProjectUsers> addMember(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(projectService.addProjectUser(memberDto))  ;

    }
    @GetMapping("/members/{id}")
    public ResponseEntity<List<ProjectUsers>> getMembers(@PathVariable int id) {
        return ResponseEntity.ok(projectService.getProjectUsers(id))  ;

    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody ProjectDTO projectDTO) {
         return ResponseEntity.ok(projectService.updateProject(projectDTO,id)) ;

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable int id) {
      return  ResponseEntity.ok(projectService.deleteProject(id)) ;
    }
}
