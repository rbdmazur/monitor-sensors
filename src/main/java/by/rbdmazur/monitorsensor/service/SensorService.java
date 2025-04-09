package by.rbdmazur.monitorsensor.service;

import by.rbdmazur.monitorsensor.repository.RangeRepository;
import by.rbdmazur.monitorsensor.repository.SensorRepository;
import by.rbdmazur.monitorsensor.repository.model.Range;
import by.rbdmazur.monitorsensor.repository.model.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final RangeRepository rangeRepository;

    public SensorService(SensorRepository repository, RangeRepository rangeRepository) {
        this.sensorRepository = repository;
        this.rangeRepository = rangeRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public void save(Sensor sensor) {
        try {
            Optional<Range> range = rangeRepository.findByFromTo(sensor.getRange().getFrom(), sensor.getRange().getTo());
            if (range.isEmpty()) {
                Range newRange = rangeRepository.save(sensor.getRange());
                sensor.setRange(newRange);
            } else {
                sensor.setRange(range.get());
            }
            Sensor.checkUnitFormat(sensor.getUnit());
            sensorRepository.save(sensor);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            Optional<Sensor> sensor = sensorRepository.findById(id);
            if (sensor.isEmpty()) {
                throw new IllegalStateException("Sensor not found");
            }
            sensorRepository.deleteById(id);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void update(Long id, Sensor sensor) {
        try {
            Optional<Sensor> oldSensorOpt = sensorRepository.findById(id);
            if (oldSensorOpt.isEmpty()) {
                throw new IllegalStateException("Sensor not found");
            }
            Sensor.checkUnitFormat(sensor.getUnit());

            Sensor oldSensor = oldSensorOpt.get();

            oldSensor.setName(sensor.getName());
            oldSensor.setModel(sensor.getModel());
            oldSensor.setUnit(sensor.getUnit());
            oldSensor.setType(sensor.getType());
            oldSensor.setLocation(sensor.getLocation());
            oldSensor.setDescription(sensor.getDescription());
            oldSensor.getRange().setFrom(sensor.getRange().getFrom());
            oldSensor.getRange().setTo(sensor.getRange().getTo());

            sensorRepository.save(oldSensor);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public List<Sensor> findByName(String name) {
        return sensorRepository.searchSensorsByName(name);
    }

    public List<Sensor> findByModel(String model) {
        return sensorRepository.searchSensorsByModel(model);
    }
}
