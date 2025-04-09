package by.rbdmazur.monitorsensor.controller;

import by.rbdmazur.monitorsensor.controller.requests.SensorRequest;
import by.rbdmazur.monitorsensor.repository.model.Sensor;
import by.rbdmazur.monitorsensor.service.SensorService;
import by.rbdmazur.monitorsensor.controller.responses.SensorsResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getSensors() {
        List<Sensor> resultList = sensorService.findAll();
        return ResponseEntity.ok().body(new SensorsResponse(resultList));
    }

    @PostMapping(path = "create")
    public ResponseEntity<?> createSensor(@RequestBody SensorRequest sensorRequest) {
        try {
            sensorService.save(sensorRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable Long id) {
        try {
            sensorService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping(path = "edit/{id}")
    public ResponseEntity<?> updateSensor(@PathVariable Long id, @RequestBody SensorRequest sensorRequest) {
        try {
            sensorService.update(id, sensorRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(path="/search/name")
    public ResponseEntity<?> findByName(@RequestParam(required = false, name="q") String name) {
        List<Sensor> resultList = sensorService.findByName(name);
        return ResponseEntity.ok().body(new SensorsResponse(resultList));
    }

    @GetMapping(path = "/search/model")
    public ResponseEntity<?> findByModel(@RequestParam(required = false, name="q") String model) {
        List<Sensor> resultList = sensorService.findByModel(model);
        return ResponseEntity.ok().body(new SensorsResponse(resultList));
    }
}
