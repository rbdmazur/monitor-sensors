package by.rbdmazur.monitorsensor.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 30, message = "Name length must be between 3 and 30")
    private String name;
    @Size(max = 15, message = "Model name length must be not greater then 15")
    private String model;
    @ManyToOne
    @JoinColumn(name = "range_id", referencedColumnName = "id")
    private Range range;
    @Enumerated(EnumType.STRING)
    private SensorType type;
    private String unit;
    @Size(max = 40, message = "Location name length must be not greater then 40")
    private String location;
    @Size(max = 200, message = "Description length must be not greater then 200")
    private String description;

    public static void checkUnitFormat(String unit) {
        if (!unit.equals("°С") && !unit.equals("bar") && !unit.equals("voltage") && !unit.equals("%")) {
            throw new IllegalArgumentException("Invalid unit format");
        }
    }
}




