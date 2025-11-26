package com.project_app.project_management.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project_app.project_management.project.Project;
import com.project_app.project_management.project.ProjectUsers;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter // or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(nullable = false, unique = true)
    private String meetingCode;

    @Enumerated(EnumType.STRING)
    private MeetingType type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
     @Enumerated(EnumType.STRING)
    private MeetingStatus  status;
    @Column(nullable = false)
     double duration ;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToMany
    @JoinTable(
            name = "meeting_participants",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "project_user_id")
    )
    private List<ProjectUsers> participants = new ArrayList<>();
    @PrePersist
    public void generateMeetingCode() {
        if (meetingCode == null || meetingCode.isEmpty()) {
            meetingCode = UUID.randomUUID().toString();
        }
    }
}