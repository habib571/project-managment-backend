package com.project_app.project_management.user;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserRepository;
import com.project_app.project_management.storage.UploadsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UploadsService uploadsService;

    public UserService(UserRepository userRepository, UploadsService uploadsService) {
        this.userRepository = userRepository;
        this.uploadsService = uploadsService;
    }
    public Map<String, Object> allUsers(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findByFullNameOrEmail(name, pageable);

        return Map.of(
                "data", usersPage.getContent(),
                "pagination", Map.of(
                        "currentPage", usersPage.getNumber(),
                        "totalPages", usersPage.getTotalPages(),
                        "totalElements", usersPage.getTotalElements(),
                        "isFirstPage", usersPage.isFirst(),
                        "isLastPage", usersPage.isLast(),
                        "pageSize", usersPage.getSize()
                )
        );
    }
   public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }
    public void saveUser(User user) {

        userRepository.save(user);
    }

    public User updateProfileImage(Integer userId, MultipartFile file) throws IOException {
        String filename = uploadsService.storeFile(file);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.setPhotoUrl(filename);
        // save and return the managed, up-to-date User
        return userRepository.save(user);
    }

}
