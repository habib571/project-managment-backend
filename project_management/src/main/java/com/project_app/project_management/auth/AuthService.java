package com.project_app.project_management.auth;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private  final  UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticatonManager, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;

}
public  boolean isUserExists(String email) {
        return userRepository.existsByEmail(email) ;
}
 public User register(RegisterUserDto input) {
        User user = new User() ;
                user.setFullName(input.getFullName());
                user.setPassword(passwordEncoder.encode(input.getPassword()));
                user.setEmail(input.getEmail());
        return  userRepository.save(user) ;


    }
    public User login(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return  userRepository.findByEmail(input.getEmail()).orElseThrow() ;
    }


}