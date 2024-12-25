package com.project_app.project_management.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginUserDto {
    private String email;
    private String password;


}