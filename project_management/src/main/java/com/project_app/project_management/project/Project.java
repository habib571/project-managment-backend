package com.project_app.project_management.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project_app.project_management.auth.User;
import com.project_app.project_management.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name ="project" )
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(length = 15)
    private  String name  ;
    private  String description ;
    private  double progress ;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User createdBy;
    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<ProjectUsers> projectUsers ;
    @OneToMany(mappedBy ="project")
    @JsonIgnore
    List<Task> tasks ;
}
