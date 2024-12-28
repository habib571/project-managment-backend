package com.project_app.project_management.project;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Getter
@Setter
public class ProjectDTO {
    private String name ;
    private String description ;
    private Date endDate;
}
