package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.domain.model.Role;
import de.lm.mybriefmarkensammlung.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest extends AbstractPostgresTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Should find id by username")
    void testFindIdByUsername() {
        // Arrange
        Long roleId = roleRepository.save(new Role("TEST_ROLE")).getId();
        User user1 = userRepository.save(new User("user1", "123456", roleId));
        User user2 = userRepository.save(new User("user2", "123456", roleId));

        // Act
        Optional<Long> optUser = userRepository.findIdByUsername(user1.getUsername());

        // Assert
        assertThat(optUser).contains(user1.getId());
    }

    @Test
    @DisplayName("Should find nothing by username")
    void testFindNothingByUsername() {
        // Arrange
        Long roleId = roleRepository.save(new Role("TEST_ROLE")).getId();
        User user1 = userRepository.save(new User("user1", "123456", roleId));

        // Act
        Optional<Long> optUser = userRepository.findIdByUsername("nothing");

        // Assert
        assertThat(optUser).isEmpty();
    }

    @Test
    @DisplayName("Should find username by id")
    void testFindUsernameById() {
        // Arrange
        Long roleId = roleRepository.save(new Role("TEST_ROLE")).getId();
        User user1 = userRepository.save(new User("user1", "123456", roleId));
        User user2 = userRepository.save(new User("user2", "123456", roleId));

        // Act
        Optional<String> optUser = userRepository.findUsernameById(user1.getId());

        // Assert
        assertThat(optUser).contains(user1.getUsername());
    }

    @Test
    @DisplayName("Should find nothing by id")
    void testFindNothingById() {
        // Arrange
        Long roleId = roleRepository.save(new Role("TEST_ROLE")).getId();
        User user1 = userRepository.save(new User("user1", "123456", roleId));

        // Act
        Optional<String> optUser = userRepository.findUsernameById(12L);

        // Assert
        assertThat(optUser).isEmpty();
    }
}