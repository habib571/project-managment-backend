package com.project_app.project_management.issue;

import lombok.*;

@Getter
@Setter   // or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {
    private int id;
    private String name ;
    private String description;
    private boolean isSolved ;
    private TagedUserDto tagedUserDto ;
    private TagedTaskDto tagedTaskDto ;
}
