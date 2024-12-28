package com.project_app.project_management.auth;

import com.project_app.project_management.project.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User ,Integer> {

    Optional<User> findByEmail(String email) ;
    boolean existsByEmail(String email);
    List<User> findAllByFullNameStartsWith(String fullName, Pageable pageable ) ;

}
