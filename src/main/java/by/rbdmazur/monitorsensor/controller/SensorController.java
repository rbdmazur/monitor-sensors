package by.rbdmazur.monitorsensor.controller;

import by.rbdmazur.monitorsensor.repository.model.Sensor;
import by.rbdmazur.monitorsensor.service.SensorService;
import by.rbdmazur.monitorsensor.controller.responses.SensorsResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public SensorsResponse getSensors() {
        List<Sensor> resultList = sensorService.findAll();
        return new SensorsResponse(resultList);
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @PostMapping(path = "create")
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

    @GetMapping(path="/search/name")
    public SensorsResponse findByName(@RequestParam(required = false) String name) {
        List<Sensor> resultList = sensorService.findByName(name);
        return new SensorsResponse(resultList);
    }

    @GetMapping(path = "/search/model")
    public SensorsResponse findByModel(@RequestParam(required = false) String model) {
        List<Sensor> resultList = sensorService.findByModel(model);
        return new SensorsResponse(resultList);
    }
}
