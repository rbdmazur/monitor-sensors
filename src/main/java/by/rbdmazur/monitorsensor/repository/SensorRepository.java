package by.rbdmazur.monitorsensor.repository;

import by.rbdmazur.monitorsensor.repository.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    @Query(value = "select * from sensors where lower(name) ~ lower(:query) or lower(model) ~ lower(:query)", nativeQuery = true)
    List<Sensor> searchSensor(String query);
}
