package com.project_app.project_management.user;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.auth.UserDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<Object> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new UserDTO().convertToUserDTO(currentUser));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<User>> allUsers(@PathVariable("name") String name , @RequestParam("page") int page , @RequestParam("size") int size) {
        List<User> users = userService.allUsers(name, page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/userById/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") Integer userId) {
        return  ResponseEntity.ok(userService.getUserById(userId)) ;

    }
    @PutMapping(
            value = "/me/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<UserDTO> updateProfileImage(
            @RequestBody() MultipartFile file     // <-- use @RequestParam
    ) {
        Integer userId = ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();

        try {
            User updated = userService.updateProfileImage(userId, file);

            UserDTO dto = new UserDTO().convertToUserDTO(updated);
            return ResponseEntity.ok(dto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}