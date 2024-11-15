package com.project_app.project_management.project;

import com.project_app.project_management.auth.User;
import jakarta.persistence.*;

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
    private User use;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUse() {
        return use;
    }

    public void setUse(User use) {
        this.use = use;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
