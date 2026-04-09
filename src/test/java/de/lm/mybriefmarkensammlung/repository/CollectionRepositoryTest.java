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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CollectionRepositoryTest extends AbstractPostgresTest {

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Long user1Id, user2Id;
    private Long cat1Id, cat2Id;

    @BeforeEach
    void setUp() {
        Role r = roleRepository.save(new Role("TEST_ROLE"));

        user1Id = userRepository.save(new User("user1", "123456", r.getId())).getId();
        user2Id = userRepository.save(new User("user2", "123456", r.getId())).getId();

        cat1Id = categoryRepository.save(new Category("cat1", 0L)).getId();
        cat2Id = categoryRepository.save(new Category("cat2", 0L)).getId();
    }


}