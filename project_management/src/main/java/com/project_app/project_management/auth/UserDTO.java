package com.project_app.project_management.auth;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  {
    private Integer id;
    private String fullName;
    private String email;
    private  String imageUrl ;

    public UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setImageUrl(imageUrl);
        return userDTO;
    }

}