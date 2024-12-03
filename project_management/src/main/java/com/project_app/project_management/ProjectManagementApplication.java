package com.project_app.project_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.project_app.project_management")
public class ProjectManagementApplication {

	public static void main(String[] args) {SpringApplication.run(ProjectManagementApplication.class, args);}

}
