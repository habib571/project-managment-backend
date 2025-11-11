package com.project_app.project_management.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project_app.project_management.auth.User;
import com.project_app.project_management.issue.Issue;
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
    private String title;
    private String description;
    @Column(nullable = false)
    private String priority;
    @Column(nullable = false)
    private String status;
    @JsonFormat (pattern = "dd-MM-yyyy")
    private Date deadline;
    @Column(nullable = true)
    private String attachment ;
    @ManyToOne
    @JoinColumn (name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private     User assignedUser;
    @OneToOne(mappedBy = "task")
    @JsonIgnore
    private Issue issue ;
}
