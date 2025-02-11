package com.project_app.project_management.issue;

import com.project_app.project_management.auth.User;
import com.project_app.project_management.project.Project;
import com.project_app.project_management.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Issue {
     @Id
     @GeneratedValue (strategy = GenerationType.IDENTITY)
     private Integer id ;
     private String name ;
     private String description ;
     private boolean isSolved ;
     @ManyToOne
     @JoinColumn(name = "project_id")
     private Project project ;
     @OneToOne
     @JoinColumn(name = "user_id")
     private User tagedUser ;
     @OneToOne
     @JoinColumn(name = "task_id")
     private Task task ;
}
