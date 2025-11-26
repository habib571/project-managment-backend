package com.project_app.project_management.auth;

import lombok.*;

@Getter
@Setter// or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @Getter
    private String token;
    @Getter
    private long expiresIn;
    private UserDTO userDto ;

    public LoginResponse setToken(String token) {
        this.token = token;
        return  this ;
    }

    public  LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this ;
    }

    public UserDTO getUser() {
        return userDto;
    }

    public LoginResponse setUser(UserDTO userDto) {
        this.userDto = userDto;
        return  this ;
    }
}
