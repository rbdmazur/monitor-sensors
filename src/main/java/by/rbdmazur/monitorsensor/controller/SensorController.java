package by.rbdmazur.monitorsensor.controller;

import by.rbdmazur.monitorsensor.repository.model.Sensor;
import by.rbdmazur.monitorsensor.service.SensorService;
import by.rbdmazur.monitorsensor.controller.responses.SensorsResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public SensorsResponse getSensors(
            @RequestParam(name = "search_q", required = false) String searchQ
    ) {
        List<Sensor> resultList;
        if (searchQ == null) {
            resultList = sensorService.findAll();
        } else {
            resultList = sensorService.findByQuery(searchQ);
        }
        return new SensorsResponse(resultList);
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
}
