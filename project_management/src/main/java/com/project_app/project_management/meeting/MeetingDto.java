package com.project_app.project_management.meeting;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingDto {
    private String title;
    private MeetingType type;
    private LocalDateTime scheduledTime;
    private Integer projectId;
    private List<Integer> participantIds;

}
