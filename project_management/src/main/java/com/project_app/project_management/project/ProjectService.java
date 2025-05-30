package com.project_app.project_management.project;
import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.task.TaskRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectService {
    final ProjectRepository projectRepository;
    final  ProjectUsersRepository projectUsersRepository;
    final UserRepository userRepository;
    final TaskRepository taskRepository;
    public ProjectService(ProjectRepository projectRepository, ProjectUsersRepository projectUsersRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.projectUsersRepository = projectUsersRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }
     public List<Project> getCreatedProjects(User user) {

        return projectRepository.findAllByCreatedBy(user) ;
     }

     public List<Project>  getAllMyProjects(User user) {
        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user) ;
         List<Integer> projectIds = projectUsers.stream()
                 .map(pu -> pu.getProject().getId())
                 .toList();
        return projectRepository.findAllByIdIn(projectIds);
    }
     public Project getProjectById(int id){

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

  public ProjectUsers addProjectUser(MemberDto memberDto) {
        Optional<User> user = userRepository.findById(memberDto.getMember_id());
        Project project  = getProjectById(memberDto.getProject_id());
        ProjectUsers projectUsers = new ProjectUsers();
        projectUsers.setProject(project);
        projectUsers.setRole(memberDto.getRole());
        projectUsers.setUser(user.get());
       return  projectUsersRepository.save(projectUsers);
  }
  public  List<ProjectUsers> getProjectUsers(int projectId) {
      return projectUsersRepository.findByProjectId(projectId);

  }
  public  ProjectUsers updateProjectUserRole(String newRole ,int id ) {
        ProjectUsers projectUsers = projectUsersRepository.findById(id).orElse(null);
      assert projectUsers != null;
      projectUsers.setRole(newRole);
         return  projectUsersRepository.save(projectUsers);
  }
  public void deleteProjectUser(int projectId) {
        projectUsersRepository.deleteById(projectId);
  }
  public Project updateProject(ProjectDTO projectDto, int projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
      assert project != null;
      project.setName(projectDto.getName());
         project.setDescription(projectDto.getDescription());
         project.setEndDate(projectDto.getEndDate());
         project.setProgress((double) taskRepository.countAllByProjectIdAndStatus(projectId, "Done") /taskRepository.countAllByProjectId(projectId));
         return projectRepository.save(project);
  }
  public Project deleteProject(int projectId) {
        return projectRepository.deleteProjectById(projectId) ;
  }

  public  void updateProgress(int id  ) {
      Project project = projectRepository.findById(id).orElse(null);
      assert project != null;
      project.setProgress((double) taskRepository.countAllByProjectIdAndStatus(id, "Done") /taskRepository.countAllByProjectId(id));

  }


}
