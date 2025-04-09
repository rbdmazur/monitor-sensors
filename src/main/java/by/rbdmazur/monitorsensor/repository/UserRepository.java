package by.rbdmazur.monitorsensor.repository;

import by.rbdmazur.monitorsensor.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
