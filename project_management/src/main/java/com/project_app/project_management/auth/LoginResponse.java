package com.project_app.project_management.auth;

public class LoginResponse {
    private String token;

    private long expiresIn;
    private UserDTO userDto ;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return  this ;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public  LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return  this ;
    }

    public UserDTO getUser() {
        return userDto;
    }

    public LoginResponse setUser(UserDTO userDto) {
        this.userDto = userDto;
        return  this ;
    }
}
