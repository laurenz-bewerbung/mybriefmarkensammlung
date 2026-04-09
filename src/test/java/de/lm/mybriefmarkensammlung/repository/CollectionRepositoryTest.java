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
import org.springframework.context.annotation.Description;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashSet;
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

    @Test
    @DisplayName("Should find collection by title")
    void testFindCollectionByTitle() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("title1", cat1Id, "", new HashSet<>(), false, null, user1Id));
        Collection col2 = collectionRepository.save(new Collection("title2", cat1Id, "", new HashSet<>(), false, null, user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("title1", null, null, null, null);

        // Assert
        assertThat(collections).hasSize(1);
        assertThat(collections.get(0).getTitle()).isEqualTo(col1.getTitle());
    }

    @Test
    @DisplayName("Should find nothing by title")
    void testFindNothingByTitle() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("title1", cat1Id, "", new HashSet<>(), false, null, user1Id));
        Collection col2 = collectionRepository.save(new Collection("title2", cat1Id, "", new HashSet<>(), false, null, user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("nothing", null, null, null, null);

        // Assert
        assertThat(collections).hasSize(0);
    }

    @Test
    @DisplayName("Should find collection by category")
    void testFindCollectionByCategory() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), false, null, user1Id));
        Collection col2 = collectionRepository.save(new Collection("test", cat2Id, "", new HashSet<>(), false, null, user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("", new Long[]{cat1Id}, null, null, null);

        // Assert
        assertThat(collections).hasSize(1);
        assertThat(collections.get(0).getCategoryId()).isEqualTo(cat1Id);
    }

    @Test
    @DisplayName("Should find nothing by category")
    void testFindNothingByCategory() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("test", cat2Id, "", new HashSet<>(), false, null, user1Id));
        Collection col2 = collectionRepository.save(new Collection("test", cat2Id, "", new HashSet<>(), false, null, user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("", new Long[]{cat1Id}, null, null, null);

        // Assert
        assertThat(collections).hasSize(0);
    }

    @Test
    @DisplayName("Should find collection by exhibition")
    void testFindCollectionByExhibition() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), true, null, user1Id));
        Collection col2 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), false, null, user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("", null, true, null, null);

        // Assert
        assertThat(collections).hasSize(1);
        assertThat(collections.get(0).getExhibition()).isEqualTo(true);
    }

    @Test
    @DisplayName("Should find nothing by exhibition")
    void testFindNothingByExhibition() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), false, null, user1Id));
        Collection col2 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), false, null, user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("", null, true, null, null);

        // Assert
        assertThat(collections).hasSize(0);
    }

    @Test
    @DisplayName("Should find collection by exhibition class")
    void testFindCollectionByExhibitionClass() {
        // Arrange
        Collection col1 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), true, "Class1", user1Id));
        Collection col2 = collectionRepository.save(new Collection("test", cat1Id, "", new HashSet<>(), true, "Class2", user1Id));

        // Act
        List<Collection> collections = collectionRepository.search("", null, true, "Class1", null);

        // Assert
        assertThat(collections).hasSize(1);
        assertThat(collections.get(0).getExhibitionClass()).isEqualTo("Class1");
    }
}