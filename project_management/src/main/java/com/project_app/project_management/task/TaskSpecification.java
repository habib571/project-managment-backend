package com.project_app.project_management.task;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> hasStatus(String status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(String priority) {
        return (root, query, cb) -> cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasDeadline(Date deadline) {
        return (root, query, cb) -> cb.equal(root.get("deadline"), deadline);
    }

    public static Specification<Task> hasProjectIds(List<Integer> projectIds) {
        return (root, query, cb) -> root.get("project").get("id").in(projectIds);
    }
}
