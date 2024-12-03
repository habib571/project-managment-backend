package com.project_app.project_management.task;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.project.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table (name = "task")
public class Task {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column (length = 15)
    private String title;
    private String description;
    @Column(nullable = false)
    private String priority;
    @Column(nullable = false)
    private String status;
    @DateTimeFormat
    private Date deadline;
    private String attachment ;
    @ManyToOne
    @JoinColumn (name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser;

}
