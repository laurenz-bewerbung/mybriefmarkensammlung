package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jdbc.test.autoconfigure.DataJdbcTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should find the path from root 'Europa' to the leaf 'DDR' in reversed order")
    void testFindCategoryPathFromRootToLeaf() {
        // Arrange
        Category c1 = categoryRepository.save(new Category("Europa", 0L));
        Category c2 = categoryRepository.save(new Category("Deutschland", c1.getId()));
        Category c3 = categoryRepository.save(new Category("DDR", c2.getId()));

        // Act
        List<Category> path = categoryRepository.findCategoryPath(c3.getId());

        // Assert
        assertThat(path).hasSize(3);

        assertThat(path.get(0).getCategory()).isEqualTo(c3.getCategory());
        assertThat(path.get(1).getCategory()).isEqualTo(c2.getCategory());
        assertThat(path.get(2).getCategory()).isEqualTo(c1.getCategory());
    }

    /*@Test
    @DisplayName("Sollte nur die Kategorie selbst zurückgeben, wenn sie keine Eltern hat")
    void testFindPathForRoot() {
        List<Category> path = categoryRepository.findCategoryPath(1L);

        assertThat(path).hasSize(1);
        assertThat(path.get(0).getCategory()).isEqualTo("Briefmarken");
    }*/
}