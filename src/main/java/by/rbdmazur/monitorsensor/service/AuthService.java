package by.rbdmazur.monitorsensor.service;

import by.rbdmazur.monitorsensor.repository.UserRepository;
import by.rbdmazur.monitorsensor.repository.model.Roles;
import by.rbdmazur.monitorsensor.repository.model.User;
import by.rbdmazur.monitorsensor.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new IllegalArgumentException("Username is already in use");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(Roles.ROLE_VIEWER);
        userRepository.save(newUser);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        var token = new UsernamePasswordAuthenticationToken(
                username,
                password,
                user.getAuthorities()
        );
        authenticationManager.authenticate(token);
        return jwtService.generateToken(user);
    }
}
