package com.project_app.project_management.task;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDto {
    private String name;
    private String description;
    //private String attachment ;
    private String priority;
    private int assignedTo ;
    private Date deadline;
    public TaskDto(int assignedTo, String attachment, String priority, String description, String name , Date deadline) {
        this.assignedTo = assignedTo;
    //    this.attachment = attachment;
        this.priority = priority;
        this.description = description;
        this.name = name;
        this.deadline = deadline;
    }

}
