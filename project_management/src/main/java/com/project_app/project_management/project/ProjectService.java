package com.project_app.project_management.project;
import com.project_app.project_management.auth.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Service
public class ProjectService {
    final ProjectRepository projectRepository;
    final  ProjectUsersRepository projectUsersRepository;
    public ProjectService(ProjectRepository projectRepository, ProjectUsersRepository projectUsersRepository) {
        this.projectRepository = projectRepository;
        this.projectUsersRepository = projectUsersRepository;
    }
     public List<Project> getCreatedProjects(User user) {
     return projectRepository.findAllByCreatedBy(user) ;
     }
     public List<Project>  getAllMyProjects(User user) {
        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user) ;
        return  projectRepository.findAllByProjectUsers(projectUsers);
    }
     public Project getProjectById(int id) {
     return projectRepository.findById(id).orElse(null);
     }
     public Project addProject(ProjectDTO project , User user) throws ParseException {
         SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
         String formattedDate = formatter.format(new Date()) ;
            Project newProject = new Project();
            newProject.setCreatedBy(user);
            newProject.setName(project.getName());
            newProject.setDescription(project.getDescription());
            newProject.setEndDate(project.getEndDate());
            newProject.setStartDate(formatter.parse(formattedDate));
            newProject.setProgress(0.0);
            Project p= projectRepository.save(newProject);
            ProjectUsers projectUsers = new ProjectUsers();
            projectUsers.setProject(p);
            projectUsers.setRole("Manager");
            projectUsers.setUser(user);
            projectUsersRepository.save(projectUsers);
        return p ;
     }

}
