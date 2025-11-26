package com.project_app.project_management.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project_app.project_management.auth.User;
import com.project_app.project_management.meeting.Meeting;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter// or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "project_users")
public class ProjectUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String role;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @CreationTimestamp
    private Date joinedAt;
    @ManyToMany(mappedBy = "participants")
    @JsonIgnore
    private List<Meeting> meetings = new ArrayList<>();


}
