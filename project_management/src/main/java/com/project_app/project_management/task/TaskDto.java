package com.project_app.project_management.task;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDto {

    @NotBlank(message = "Task name is required")
    @Size(max = 25, message = "Task name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Task description is required")
    @Size(max = 50, message = "Task name cannot exceed 255 characters")
    private String description;

    @NotBlank(message = "Task priority is required")
    private String priority;

    @NotNull(message = "Assigned user is required")
    private Integer assignedTo;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be a future date")
    private Date deadline;

    @NotBlank(message = "Task status is required")
    private String status;

    public TaskDto(int assignedTo, String attachment, String status, String priority, String description, String name, Date deadline) {
        this.assignedTo = assignedTo;
        this.priority = priority;
        this.description = description;
        this.name = name;
        this.deadline = deadline;
        this.status = status;
    }
}