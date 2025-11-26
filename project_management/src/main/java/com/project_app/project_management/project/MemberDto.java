package com.project_app.project_management.project;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter // or @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

 @NotNull(message = "Member ID is required")
 @Min(value = 1, message = "Member ID must be a positive number")
 private Integer member_id;

 @NotBlank(message = "Role is required")
 @Size(max = 50, message = "Role must be less than 50 characters")
 private String role;

 @NotNull(message = "Project ID is required")
 @Min(value = 1, message = "Project ID must be a positive number")
 private Integer project_id;


}