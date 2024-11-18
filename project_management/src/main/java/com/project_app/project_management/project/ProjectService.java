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
    final  ProjectUsersRepository projectUsersRepository;
    public ProjectService(ProjectRepository projectRepository, ProjectUsersRepository projectUsersRepository) {
        this.projectRepository = projectRepository;
        this.projectUsersRepository = projectUsersRepository;
    }
     public List<Project> getAllProjects(User user) {
     return projectRepository.findAllByCreatedBy(user) ;

     }
     public Project getProjectById(int id) {
     return projectRepository.findById(id).orElse(null);

     }
     public Project addProject(ProjectDTO project , User user) {
           Project newProject = new Project();
           newProject.setCreatedBy(user);
           newProject.setName(project.getName());
           newProject.setDescription(project.getDescription());
            Project p=  projectRepository.save(newProject);
            ProjectUsers projectUsers = new ProjectUsers();
            projectUsers.setProject(p);
            projectUsers.setRole("Manager");
            projectUsers.setUser(user);
            projectUsersRepository.save(projectUsers);

        return p ;
     }

}
