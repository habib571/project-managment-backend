package com.project_app.project_management.auth;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Paths;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthService authenticationService;


    public AuthenticationController(JwtService jwtService, AuthService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService ;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        if (authenticationService.isUserExists(registerUserDto.getEmail())) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatusCode.valueOf(400), "User with this email already exists."
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
        }

        User registeredUser = authenticationService.register(registerUserDto) ;
        String jwtToken = jwtService.generateToken(registeredUser);
        UserDTO  userDTO= new UserDTO().convertToUserDTO(registeredUser) ;
        String defaultProfileImagePath = Paths.get("storage", "default-profile.png").toString();

        userDTO.setImageUrl(defaultProfileImagePath);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()).setUser(new UserDTO().convertToUserDTO(registeredUser));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime()).setUser(new UserDTO().convertToUserDTO(authenticatedUser));
        return ResponseEntity.ok(loginResponse);
    }
}   