package com.project_app.project_management.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<Map<String, Object>> getProjectActivities(
            @PathVariable int projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(activityService.getProjectActivities(projectId, page, size));
    }
}
