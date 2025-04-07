package by.rbdmazur.monitorsensor.repository;

import by.rbdmazur.monitorsensor.repository.model.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RangeRepository extends JpaRepository<Range, Long> {
    @Query(value = "select * from ranges where \"from\" = :from and \"to\" = :to", nativeQuery = true)
    Optional<Range> findByFromTo(Integer from, Integer to);
}
