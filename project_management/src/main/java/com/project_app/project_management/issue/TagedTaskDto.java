package com.project_app.project_management.issue;

import lombok.*;

@Getter
@Setter// or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagedTaskDto {
    private int id ;
    private String taskName ;
}
