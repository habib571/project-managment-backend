package com.project_app.project_management.task;


import com.project_app.project_management.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {
     Task findById(int id);
     List<Task> findAllByDeadline(Date deadline);
     List<Task> findAllByStatus(String status);
     Page<Task> findAllByProject_Id(int project_id, Pageable pageable);
     List<Task> findAllByAssignedUser(User assignedUser);
    // List<Task> findAllBy(Specification<Task> spec ,Pageable pageable);
    @Query(value = "SELECT * FROM task t " +
            "WHERE (:status IS NULL OR t.status = :status) " +
            "  AND (:priority IS NULL OR t.priority = :priority) " +
            "  AND (:deadline IS NULL OR DATE(t.deadline) = to_date(:deadline, 'DD-MM-YYYY')) " +
            "  AND t.project_id IN :projectIds",
            nativeQuery = true)
    List<Task> findFilteredTasks(@Param("status") String status,
                                 @Param("priority") String priority,
                                 @Param("deadline") String deadline,
                                 @Param("projectIds") List<Integer> projectIds,
                                 Pageable pageable);
     List<Task> findAllByProjectIdInAndTitleStartingWith(List<Integer> project_id, String title ,Pageable pageable);
     int countAllByProjectId(int project_id);
     int countAllByProjectIdAndStatus(int project_id, String status);
}