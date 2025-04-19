package com.project_app.project_management.project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMemberRoleDto {
    @NotBlank(message = "Role is required")
    @Size(max = 12, message = "Role must be less than 50 characters")
    private String role;

}
