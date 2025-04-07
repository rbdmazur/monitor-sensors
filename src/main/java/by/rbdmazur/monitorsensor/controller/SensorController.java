package by.rbdmazur.monitorsensor.controller;

import by.rbdmazur.monitorsensor.repository.model.Sensor;
import by.rbdmazur.monitorsensor.service.SensorService;
import by.rbdmazur.monitorsensor.controller.responses.SensorsResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public SensorsResponse getSensors() {
        SensorsResponse response = new SensorsResponse(sensorService.findAll());
        return response;
    }

    @PostMapping
    public void createSensor(@RequestBody Sensor sensor) {
        sensorService.save(sensor);
    }

    @DeleteMapping(path = "delete/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorService.delete(id);
    }

    @PutMapping(path = "edit/{id}")
    public void updateSensor(@PathVariable Long id, @RequestBody Sensor sensor) {
        sensorService.update(id, sensor);
    }

    @GetMapping(path = "search/name")
    public SensorsResponse getSensorsByName(@RequestParam String name) {
        SensorsResponse response = new SensorsResponse(sensorService.findByName(name));
        return response;
    }

    @GetMapping(path = "search/model")
    public SensorsResponse getSensorsByModel(@RequestParam String model) {
        SensorsResponse response = new SensorsResponse(sensorService.findByModel(model));
        return response;
    }
}
