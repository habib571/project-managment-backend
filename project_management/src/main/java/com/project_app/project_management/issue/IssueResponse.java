package com.project_app.project_management.issue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueResponse {
    private int id;
    private String name ;
    private String description;
    private boolean isSolved ;
    private TagedUserDto tagedUserDto ;
    private TagedTaskDto tagedTaskDto ;
}
