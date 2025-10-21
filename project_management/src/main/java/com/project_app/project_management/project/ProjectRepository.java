package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project ,Integer> {
    @Override
    Optional<Project> findById(Integer id);
    @Override
    Page<Project> findAll();
    List<Project> findAllByCreatedBy(User createdBy);
    Page<Project> findAllByIdIn(List<Integer> ids ,Pageable pageable);
    Project deleteProjectById(Integer id);


}
