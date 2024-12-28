package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjectUsersRepository extends CrudRepository<ProjectUsers, Integer> {
List<ProjectUsers> findByProjectId(int projectId);
List<ProjectUsers> findAllByUser(User user);
}
