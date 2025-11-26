package com.project_app.project_management.issue;

import lombok.*;

@Getter
@Setter  // or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
     private String name;
     private String description;
     private int task_id ;
     private int user_id;
}
