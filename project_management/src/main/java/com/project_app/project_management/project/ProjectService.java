package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    final ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
     public List<Project> getAllProjects() {
     return projectRepository.findAll() ;

     }
     public Project getProjectById(int id) {
     return projectRepository.findById(id).orElse(null);

     }
     public Project addProject(ProjectDTO project , User user) {
        Project newProject = new Project();
           newProject.setCreatedBy(user);
           newProject.setName(project.getName());
           newProject.setDescription(project.getDescription());
        return projectRepository.save(newProject);
     }

}
