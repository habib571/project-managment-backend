package com.project_app.project_management.auth;

import com.project_app.project_management.project.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User ,Integer> {

    Optional<User> findByEmail(String email) ;
    boolean existsByEmail(String email);
    @Query ("SELECT DISTINCT u FROM User u WHERE u.fullName LIKE :input% OR u.email LIKE :input%")
    List<User> findByFullNameOrEmail(@Param ("input") String input, Pageable pageable);
    User findById(int id);

}
