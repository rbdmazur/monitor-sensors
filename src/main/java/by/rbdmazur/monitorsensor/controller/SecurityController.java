package by.rbdmazur.monitorsensor.controller;

import by.rbdmazur.monitorsensor.controller.requests.SignRequest;
import by.rbdmazur.monitorsensor.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final AuthService authService;

    @PostMapping("/reg")
    public ResponseEntity<?> registerUser(@RequestBody SignRequest signRequest) {
        try {
            authService.register(signRequest.getUsername(), signRequest.getPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody SignRequest signRequest) {
        try {
            String res = authService.login(signRequest.getUsername(), signRequest.getPassword());
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
