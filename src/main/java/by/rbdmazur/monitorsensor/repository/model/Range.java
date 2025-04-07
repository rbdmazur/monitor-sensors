package by.rbdmazur.monitorsensor.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ranges")
public class Range {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "\"from\"")
    @Min(value = 0, message = "Range must contains only positive integers.")
    private Integer from;
    @Column(name = "\"to\"")
    @Min(value = 0, message = "Range must contains only positive integers.")
    private Integer to;

    public Range(Integer to) {
        this.from = 0;
        this.to = to;
    }
}
