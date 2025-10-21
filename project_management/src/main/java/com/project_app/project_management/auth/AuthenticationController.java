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
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthService authenticationService;
    private final  RefreshTokenService refreshTokenService;
    private  final  RefreshTokenRepository refreshTokenRepository;


    public AuthenticationController(JwtService jwtService, AuthService authenticationService, RefreshTokenRepository refreshTokenRepository, RefreshTokenService refreshTokenService, RefreshTokenRepository refreshTokenRepository1) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService ;
        this.refreshTokenService = refreshTokenService;
        this.refreshTokenRepository = refreshTokenRepository1;
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
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getAccessExpirationTime()).setUser(new UserDTO().convertToUserDTO(registeredUser));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getAccessExpirationTime()).setUser(new UserDTO().convertToUserDTO(authenticatedUser));
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload) {
        String requestToken = payload.get("refreshToken");
        return refreshTokenRepository.findByToken(requestToken)
                .map(token -> {
                    if (refreshTokenService.isTokenExpired(token)) {
                        refreshTokenRepository.delete(token);
                        return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
                    }
                    String newJwt = jwtService.generateToken(token.getUser());
                    return ResponseEntity.ok(Map.of("token", newJwt));
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }
}   