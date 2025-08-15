package com.project_app.project_management.meeting;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.project.ProjectRepository;
import com.project_app.project_management.project.ProjectUsers;
import com.project_app.project_management.project.ProjectUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        return meetingRepository.save(meeting);
    }
    public List<Meeting> findMeetingByProjectId(int projectId) {
        return meetingRepository.findMeetingByProjectId(projectId);
    }

}
