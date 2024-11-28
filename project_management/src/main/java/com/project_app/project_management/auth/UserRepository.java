package com.project_app.project_management.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User ,Integer> {

    Optional<User> findByEmail(String email) ;
    boolean existsByEmail(String email);
}
