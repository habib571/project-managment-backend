package com.project_app.project_management.issue;

import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.project.ProjectRepository;
import com.project_app.project_management.task.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    final IssueRepository issueRepository;
    final UserRepository userRepository;
    final TaskRepository taskRepository;
    final ProjectRepository projectRepository;

    public IssueService(IssueRepository issueRepository, UserRepository userRepository, TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }
    public IssueResponse addIssue(IssueDto issueDto  ,int projectId) {
        Issue issue = new Issue();
        issue.setName(issueDto.getName());
        issue.setDescription(issueDto.getDescription());
        issue.setTask(taskRepository.findById(issueDto.getTask_id()));
        issue.setTagedUser(userRepository.findById(issueDto.getUser_id()));
        issue.setProject(projectRepository.findById(projectId).get());
        issue.setSolved(false);
        return mapToIssueResponse(issueRepository.save(issue));
    }
    public List<IssueResponse> getAllIssues(int projectId) {
        List<Issue> issues = issueRepository.findByProject_Id(projectId);
        return issues.stream()
                .map(this::mapToIssueResponse)
                .toList();
    }

    private IssueResponse mapToIssueResponse(Issue issue) {
        IssueResponse issueResponse = new IssueResponse();
        issueResponse.setId(issue.getId());
        issueResponse.setName(issue.getName());
        issueResponse.setDescription(issue.getDescription());
        issueResponse.setSolved(issue.isSolved());

        TagedUserDto tagedUserDto = new TagedUserDto();
        if (issue.getTagedUser() != null) {
            tagedUserDto.setUser_id(issue.getTagedUser().getId());
            tagedUserDto.setName(issue.getTagedUser().getFullName());
            tagedUserDto.setImageUrl(issue.getTagedUser().getPhotoUrl());
        }
        issueResponse.setTagedUserDto(tagedUserDto);

        TagedTaskDto tagedTaskDto = new TagedTaskDto();

        if (issue.getTask() != null) {
            tagedTaskDto.setId(issue.getTask().getId());
            tagedTaskDto.setTaskName(issue.getTask().getTitle());
        }
        issueResponse.setTagedTaskDto(tagedTaskDto);
        return issueResponse;
    }
    public IssueResponse markAsReSolved(int issueId) {
     Optional<Issue> issue = issueRepository.findById(issueId);
        issue.get().setSolved(true);
       issueRepository.save(issue.get());
       return mapToIssueResponse(issue.get());

    }

}
