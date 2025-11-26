package com.project_app.project_management.issue;

import lombok.*;

@Getter
@Setter // or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagedUserDto {
    private int user_id;
    private  String name ;
    private String imageUrl ;
}
