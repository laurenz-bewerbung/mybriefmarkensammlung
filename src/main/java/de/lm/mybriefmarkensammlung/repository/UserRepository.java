package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT id FROM \"user\" WHERE username = :username")
    Optional<Long> findIdByUsername(String username);

    @Query("SELECT username FROM \"user\" WHERE id = :id")
    Optional<String> findUsernameById(@Param("id") Long id);
}
