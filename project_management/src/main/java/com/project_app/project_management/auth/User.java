    package com.project_app.project_management.auth;
import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.project_app.project_management.issue.Issue;
    import com.project_app.project_management.project.Project;
    import com.project_app.project_management.project.ProjectUsers;
    import com.project_app.project_management.task.Task;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;
    import org.hibernate.annotations.CreationTimestamp;
    import org.hibernate.annotations.UpdateTimestamp;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Collection;
    import java.util.Date;
    import java.util.List;
 @Getter
 @Setter
    @Table(name = "users")
    @Entity
    public class User implements UserDetails {
         @Id
         @GeneratedValue(strategy = GenerationType.AUTO)
         @Column(nullable = false)
         private Integer id;
         @Column(nullable = false)
         private String fullName;
         @Column(unique = true, length = 100, nullable = false)
         private String email;
         @Column(nullable = false)
         @JsonIgnore
         private String password;
         @Column(nullable = true)
         private String  photoUrl ;
         @JsonIgnore
         @OneToMany(mappedBy = "createdBy" ,cascade = CascadeType.ALL )
         private List<Project> ownedProjects ;
         @JsonIgnore
         @OneToMany( cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
         private List<ProjectUsers> projectUsers ;
         @OneToMany( mappedBy = "assignedUser", cascade = CascadeType.ALL)
         @JsonIgnore
         private List<Task> tasks ;
         @OneToOne(mappedBy = "tagedUser", cascade = CascadeType.ALL)
         @JsonIgnore
         private Issue issue ;
         @CreationTimestamp
         @Column(updatable = false, name = "created_at")
         private Date createdAt;
         @UpdateTimestamp
         @Column(name = "updated_at")
         private Date updatedAt;
        @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnore
         private RefreshToken refreshToken;
         @Override
         public Collection<? extends GrantedAuthority> getAuthorities() {return List.of();}
         @Override
         public String getPassword() {
            return password;
        }
         @Override
         public String getUsername() {
            return email;
        }
         @Override
         public boolean isAccountNonExpired() {
            return true;
        }
         @Override
         public boolean isAccountNonLocked() {
            return true;
        }
         @Override
         public boolean isCredentialsNonExpired() {
            return true;
        }
         @Override
         public boolean isEnabled() {
            return true;
        }


    }
