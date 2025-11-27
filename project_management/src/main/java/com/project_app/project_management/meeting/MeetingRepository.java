package com.project_app.project_management.meeting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetingRepository  extends CrudRepository<Meeting, Integer> {
    public Page<Meeting> findMeetingByProjectId(int meetingId , Pageable pageable );

}
