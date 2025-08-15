package com.project_app.project_management.project;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class ProjectDTO {

    @NotBlank(message = "Project name is required")
    @Size(max = 15, message = "Project name must be less than 15 characters")
    private String name;

    @NotBlank(message = "Project description is required")
    @Size(max = 50, message = "Project description must be less than 50 characters")
    private String description;

    @NotNull(message = "End date is required")
//   @Future(message = "End date must be in the future")
    private Date endDate;

}