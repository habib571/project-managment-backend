package com.project_app.project_management.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project_app.project_management.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who triggered the action
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // The project where it happened
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Optional references for detail (e.g., a task or issue ID)
    private String entityType; // e.g. "TASK", "ISSUE", "MEETING"
    private Long entityId; // e.g. the task ID

    // Human-readable message
    @Column(length = 255)
    private String message;

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt;
}
