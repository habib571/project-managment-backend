package com.project_app.project_management.task;


import com.project_app.project_management.auth.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    public Task findById(int id);
    public List<Task> findAllByDeadline(Date deadline);
    public List<Task> findAllByStatus(String status);
    public List<Task> findAllByProject_Id(int project_id , Pageable pageable);
    public List<Task> findAllByAssignedUser(User assignedUser);

}
