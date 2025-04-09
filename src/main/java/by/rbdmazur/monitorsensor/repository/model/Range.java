package by.rbdmazur.monitorsensor.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ranges")
public class Range {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "\"from\"")
    @Min(value = 0, message = "Range must contains only positive integers.")
    private Integer from = 0;
    @Column(name = "\"to\"")
    @Min(value = 0, message = "Range must contains only positive integers.")
    @NotNull
    private Integer to;

    public Range(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public Range(Integer to) {
        this.to = to;
    }

    public static void checkRange(Range range) {
        if (range.from >= range.to ) {
            throw new IllegalArgumentException("Range parameter from must be less than to");
        }
    }

    public static void checkRange(Integer from, Integer to) {
        if (from >= to) {
            throw new IllegalArgumentException("Range parameter from must be less than to");
        }
    }
}
