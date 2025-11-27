package com.project_app.project_management.meeting;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.project.ProjectRepository;
import com.project_app.project_management.project.ProjectUsers;
import com.project_app.project_management.project.ProjectUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private  final ProjectRepository projectRepository;
    private final ProjectUsersRepository projectUsersRepository;

    public Meeting save(MeetingDto meetingDto) {
        var project = projectRepository.findById(meetingDto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<ProjectUsers> participants = (List<ProjectUsers>) projectUsersRepository.findAllById(meetingDto.getParticipantIds());

        if (participants.size() != meetingDto.getParticipantIds().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Project Not found") ;
        }

        boolean allSameProject = participants.stream()
                .allMatch(pu -> pu.getProject().getId().equals(project.getId()));
        if (!allSameProject) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST , "All participants must belong to the same project");
        }
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDto.getTitle());
        meeting.setType(meetingDto.getType());
        meeting.setStatus(MeetingStatus.CREATED);
        meeting.setScheduledTime(meetingDto.getScheduledTime());
        meeting.setProject(project);
        meeting.setParticipants(participants);
        meeting.setDuration(meetingDto.getDuration());
        return meetingRepository.save(meeting);
    }
    public Map<String ,Object> findMeetingByProjectId(int projectId ,int page , int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Meeting> meetings = meetingRepository.findMeetingByProjectId(projectId , pageable) ;
        return Map.of(
                "data", meetings.getContent(),
                "pagination", Map.of(
                        "currentPage", meetings.getNumber(),
                        "totalPages", meetings.getTotalPages(),
                        "totalElements", meetings.getTotalElements(),
                        "isFirstPage", meetings.isFirst(),
                        "isLastPage", meetings.isLast(),
                        "pageSize", meetings.getSize()
                )
        );
    }
    public ResponseEntity<Object> updateStatus(Integer meetingId, MeetingStatus newStatus) {
        Optional<Meeting> meetingOpt = meetingRepository.findById(meetingId);

        if (meetingOpt.isEmpty()) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND,
                    "Meeting with id " + meetingId + " not found"
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
        }

        Meeting meeting = meetingOpt.get();
        meeting.setStatus(newStatus);
        Meeting updated = meetingRepository.save(meeting);

        return ResponseEntity.ok(updated);

    }
}
