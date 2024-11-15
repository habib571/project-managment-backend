package com.project_app.project_management.user;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
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
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
   public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }


}
