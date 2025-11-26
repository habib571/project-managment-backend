package com.project_app.project_management.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;
    @PostMapping()
    public ResponseEntity<Meeting> createMeeting(@RequestBody MeetingDto meetingDto) {
        return ResponseEntity.ok(meetingService.save(meetingDto));
    }

    @GetMapping("/all/{projectId}")
    public ResponseEntity<List<Meeting>> getAllMeetings(@PathVariable Integer projectId) {
        List<Meeting> meeting = meetingService.findMeetingByProjectId(projectId);
        return ResponseEntity.ok(meeting);
    }
    @PatchMapping("update-status/{meetingId}")
    public  ResponseEntity<?> updateStatus(@PathVariable Integer meetingId, @RequestBody MeetingStatus newStatus) {
       return ResponseEntity.ok(meetingService.updateStatus(meetingId, newStatus));
    }
}
