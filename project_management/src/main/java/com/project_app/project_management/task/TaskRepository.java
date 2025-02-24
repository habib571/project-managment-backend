package com.project_app.project_management.task;


import com.project_app.project_management.auth.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
     Task findById(int id);
     List<Task> findAllByDeadline(Date deadline);
     List<Task> findAllByStatus(String status);
     List<Task> findAllByProject_Id(int project_id, Pageable pageable);
     List<Task> findAllByAssignedUser(User assignedUser);
     List<Task> findAllBy(Specification<Task> spec ,Pageable pageable);
     List<Task> findAllByProjectIdInAndTitleStartingWith(List<Integer> project_id, String title ,Pageable pageable);
}