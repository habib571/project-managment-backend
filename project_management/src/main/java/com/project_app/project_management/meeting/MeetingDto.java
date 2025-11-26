package com.project_app.project_management.meeting;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter  // or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {
    private String title;
    private MeetingType type;
    private LocalDateTime scheduledTime;
    private Integer projectId;
    private double duration ;
    private List<Integer> participantIds;

}
