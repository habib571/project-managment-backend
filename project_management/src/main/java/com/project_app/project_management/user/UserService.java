package com.project_app.project_management.user;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> allUsers(String name , int page , int size) {
        return new ArrayList<>(userRepository.findByFullNameOrEmail(name, Pageable.ofSize(size).withPage(page)
        ));
    }
   public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
