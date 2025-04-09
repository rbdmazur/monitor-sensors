package by.rbdmazur.monitorsensor.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SensorRequest {
    private String name;
    private String model;
    private String from;
    private String to;
    private String type;
    private String units;
    private String location;
    private String description;
}
