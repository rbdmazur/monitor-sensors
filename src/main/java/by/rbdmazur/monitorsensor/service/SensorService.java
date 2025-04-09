package by.rbdmazur.monitorsensor.service;

import by.rbdmazur.monitorsensor.controller.requests.SensorRequest;
import by.rbdmazur.monitorsensor.repository.RangeRepository;
import by.rbdmazur.monitorsensor.repository.SensorRepository;
import by.rbdmazur.monitorsensor.repository.model.Range;
import by.rbdmazur.monitorsensor.repository.model.Sensor;
import by.rbdmazur.monitorsensor.repository.model.SensorType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

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

    public void save(SensorRequest sensorRequest) {
        Sensor sensor = sensorRequestToSensor(sensorRequest);
        try {
            Optional<Range> range = rangeRepository.findByFromTo(sensor.getRange().getFrom(), sensor.getRange().getTo());
            if (range.isEmpty()) {
                if (sensor.getRange().getTo() == null) {
                    throw new IllegalArgumentException("Range to can't be null");
                }
                Range.checkRange(sensor.getRange());
                Range newRange = rangeRepository.save(sensor.getRange());
                sensor.setRange(newRange);
            } else {
                sensor.setRange(range.get());
            }
            if (sensor.getUnit() != null) {
                Sensor.checkUnitFormat(sensor.getUnit());
            }
            sensorRepository.save(sensor);
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException(getExceptionMessage(e));
        }
    }

    public void delete(Long id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isEmpty()) {
            throw new IllegalStateException("Sensor not found");
        }
        sensorRepository.deleteById(id);
    }

    public void update(Long id, SensorRequest sensorRequest) {
        System.out.println(sensorRequest);
        try {
            Optional<Sensor> oldSensorOpt = sensorRepository.findById(id);
            if (oldSensorOpt.isEmpty()) {
                throw new IllegalStateException("Sensor not found");
            }

            Sensor oldSensor = oldSensorOpt.get();
            if (sensorRequest.getName() != null) {
                oldSensor.setName(sensorRequest.getName());
            }
            if (sensorRequest.getModel() != null) {
                oldSensor.setModel(sensorRequest.getModel());
            }
            if (sensorRequest.getTo() != null) {
                Integer to = Integer.parseInt(sensorRequest.getTo());
                if (sensorRequest.getFrom() != null) {
                    Integer from = Integer.parseInt(sensorRequest.getFrom());
                    Range.checkRange(from, to);
                    Optional<Range> range = rangeRepository.findByFromTo(from, to);
                    if (range.isEmpty()) {
                        oldSensor.setRange(rangeRepository.save(new Range(from, to)));
                    } else {
                        oldSensor.setRange(range.get());
                    }
                } else {
                    Range.checkRange(oldSensor.getRange().getFrom(), to);
                    Optional<Range> range = rangeRepository.findByFromTo(oldSensor.getRange().getFrom(), to);
                    if (range.isEmpty()) {
                        oldSensor.setRange(rangeRepository.save(new Range(oldSensor.getRange().getFrom(), to)));
                    } else {
                        oldSensor.setRange(range.get());
                    }
                }
            } else if (sensorRequest.getFrom() != null) {
                Integer from = Integer.parseInt(sensorRequest.getFrom());
                Range.checkRange(from, oldSensor.getRange().getTo());
                Optional<Range> range = rangeRepository.findByFromTo(from, oldSensor.getRange().getTo());
                if (range.isEmpty()) {
                    oldSensor.setRange(rangeRepository.save(new Range(from, oldSensor.getRange().getTo())));
                } else {
                    oldSensor.setRange(range.get());
                }
            }

            if (sensorRequest.getType() != null) {
                Sensor.checkTypeFormat(sensorRequest.getType());
                oldSensor.setType(SensorType.valueOf(sensorRequest.getType()));
            }
            if (sensorRequest.getUnits() != null) {
                Sensor.checkUnitFormat(sensorRequest.getUnits());
                oldSensor.setUnit(sensorRequest.getUnits());
            }
            if (sensorRequest.getLocation() != null) {
                oldSensor.setLocation(sensorRequest.getLocation());
            }
            if (sensorRequest.getDescription() != null) {
                oldSensor.setDescription(sensorRequest.getDescription());
            }

            sensorRepository.save(oldSensor);
        } catch (ConstraintViolationException e) {
            throw new IllegalArgumentException(getExceptionMessage(e));
        } catch (TransactionSystemException e) {
            throw new IllegalArgumentException("Invalid parameters");
        }
    }

    public List<Sensor> findByName(String name) {
        if (name.isBlank()) {
            return sensorRepository.findAll();
        } else {
            return sensorRepository.searchSensorsByName(name);
        }
    }

    public List<Sensor> findByModel(String model) {
        if (model.isBlank()) {
            return sensorRepository.findAll();
        } else {
            return sensorRepository.searchSensorsByModel(model);
        }
    }

    private String getExceptionMessage(ConstraintViolationException e) {
        StringBuilder mes = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            mes.append(violation.getPropertyPath().toString()).append(": ").append(violation.getMessage()).append("\n");
        }
        return mes.toString();
    }

    private Sensor sensorRequestToSensor(SensorRequest sensorRequest) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorRequest.getName());
        sensor.setModel(sensorRequest.getModel());
        sensor.setRange(new Range());
        if (sensorRequest.getFrom() != null) {
            sensor.getRange().setFrom(Integer.valueOf(sensorRequest.getFrom()));
        }
        if (sensorRequest.getTo() != null) {
            sensor.getRange().setTo(Integer.valueOf(sensorRequest.getTo()));
        }
        Sensor.checkTypeFormat(sensorRequest.getType());
        sensor.setType(SensorType.valueOf(sensorRequest.getType()));
        sensor.setUnit(sensorRequest.getUnits());
        sensor.setLocation(sensorRequest.getLocation());
        sensor.setDescription(sensorRequest.getDescription());
        return sensor;
    }
}
