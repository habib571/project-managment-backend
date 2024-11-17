package com.project_app.project_management.project;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project ,Integer> {
    @Override
    Optional<Project> findById(Integer id);

    @Override
    List<Project> findAll();
}
