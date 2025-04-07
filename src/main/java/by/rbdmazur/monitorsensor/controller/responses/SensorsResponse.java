package by.rbdmazur.monitorsensor.controller.responses;

import by.rbdmazur.monitorsensor.repository.model.Sensor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SensorsResponse {
    private List<Sensor> sensors;
}
