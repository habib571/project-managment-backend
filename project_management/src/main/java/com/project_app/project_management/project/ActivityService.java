package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ProjectRepository projectRepository;
    public void log(User user, Project project, String entityType, Long entityId, String message) {
        Activity activity = new Activity();
        activity.setUser(user);
        activity.setProject(project);
        activity.setEntityType(entityType);
        activity.setEntityId(entityId);
        activity.setMessage(message);
        activityRepository.save(activity);
    }

    public Map<String, Object> getProjectActivities(int projectId, int page, int size) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Activity> activities = activityRepository.findByProjectOrderByCreatedAtDesc(project, pageable);

        return Map.of(
                "data", activities.getContent(),
                "pagination", Map.of(
                        "currentPage", activities.getNumber(),
                        "totalPages", activities.getTotalPages(),
                        "totalElements", activities.getTotalElements(),
                        "isFirstPage", activities.isFirst(),
                        "isLastPage", activities.isLast(),
                        "pageSize", activities.getSize()
                )
        );
    }
}