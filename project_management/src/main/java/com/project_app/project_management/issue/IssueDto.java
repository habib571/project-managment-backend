package com.project_app.project_management.issue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueDto {
     private String name;
     private String description;
     private int task_id ;
     private int user_id;
}
