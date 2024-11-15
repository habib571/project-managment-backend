package com.project_app.project_management.auth;

public class RegisterResponse {
    private  String email ;
    private  String fullName ;

    public String getEmail() {
        return email;
    }

    public RegisterResponse setEmail(String email) {
        this.email = email;
        return  this ;
    }

    public String getFullName() {
        return fullName;
    }

    public RegisterResponse setFullName(String fullName) {
        this.fullName = fullName;
        return  this ;
    }
}
