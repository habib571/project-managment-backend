package com.project_app.project_management.project;
import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.task.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUsersRepository projectUsersRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectUsersRepository projectUsersRepository,
                          UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.projectUsersRepository = projectUsersRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public List<Project> getCreatedProjects(User user) {
        return projectRepository.findAllByCreatedBy(user);
    }

    public Map<String, Object> getAllMyProjects(User user, int page, int size) {
        List<ProjectUsers> projectUsers = projectUsersRepository.findAllByUser(user);


        List<Integer> projectIds = projectUsers.stream()
                .map(pu -> pu.getProject().getId())
                .toList();

        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectRepository.findAllByIdIn(projectIds, pageable);

        return Map.of(
                "data", projects.getContent(),
                "pagination", Map.of(
                        "currentPage", projects.getNumber(),
                        "totalPages", projects.getTotalPages(),
                        "totalElements", projects.getTotalElements(),
                        "isFirstPage", projects.isFirst(),
                        "isLastPage", projects.isLast(),
                        "pageSize", projects.getSize()
                )
        );
    }

    public Project getProjectById(int id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with ID " + id));
    }

    public Project addProject(ProjectDTO project, User user) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(new Date());

        Project newProject = new Project();
        newProject.setCreatedBy(user);
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setEndDate(project.getEndDate());
        newProject.setStartDate(formatter.parse(formattedDate));
        newProject.setProgress(0.0);

        Project savedProject = projectRepository.save(newProject);

        ProjectUsers projectUsers = new ProjectUsers();
        projectUsers.setProject(savedProject);
        projectUsers.setRole("Manager");
        projectUsers.setUser(user);
        projectUsersRepository.save(projectUsers);

        return savedProject;
    }

    public ProjectUsers addProjectUser(MemberDto memberDto) {
        User user = userRepository.findById(memberDto.getMember_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        Project project = projectRepository.findById(memberDto.getProject_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        ProjectUsers projectUsers = new ProjectUsers();
        projectUsers.setProject(project);
        projectUsers.setRole(memberDto.getRole());
        projectUsers.setUser(user);

        return projectUsersRepository.save(projectUsers);
    }

    public List<ProjectUsers> getProjectUsers(int projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with ID " + projectId);
        }
        return projectUsersRepository.findByProjectId(projectId);
    }

    public ProjectUsers updateProjectUserRole(String newRole, int id) {
        ProjectUsers projectUsers = projectUsersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project user not found."));

        projectUsers.setRole(newRole);
        return projectUsersRepository.save(projectUsers);
    }

    public void deleteProjectUser(int projectUserId) {
        if (!projectUsersRepository.existsById(projectUserId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project user not found.");
        }
        projectUsersRepository.deleteById(projectUserId);
    }

    public Project updateProject(ProjectDTO projectDto, int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setEndDate(projectDto.getEndDate());
        project.setProgress(
                (double) taskRepository.countAllByProjectIdAndStatus(projectId, "Done") /
                        Math.max(1, taskRepository.countAllByProjectId(projectId)) // avoid /0
        );

        return projectRepository.save(project);
    }

    public Project deleteProject(int projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
        projectRepository.delete(project);
        return project;
    }

    public void updateProgress(int id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));
        project.setProgress(
                (double) taskRepository.countAllByProjectIdAndStatus(id, "Done") /
                        Math.max(1, taskRepository.countAllByProjectId(id))
        );
        projectRepository.save(project);
    }
}