package by.rbdmazur.monitorsensor.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SignRequest {
    private String username;
    private String password;
}
