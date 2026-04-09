package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Category;
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
class CategoryRepositoryTest extends AbstractPostgresTest {

    @Autowired
    private CategoryRepository categoryRepository;

    /// findCategoryPath

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

    @Test
    @DisplayName("Should find the path from root 'Europa' to the internal node 'Deutschland' in reversed order")
    void testFindCategoryPathFromRootToInternalNode() {
        // Arrange
        Category c1 = categoryRepository.save(new Category("Europa", 0L));
        Category c2 = categoryRepository.save(new Category("Deutschland", c1.getId()));
        categoryRepository.save(new Category("DDR", c2.getId()));

        // Act
        List<Category> path = categoryRepository.findCategoryPath(c2.getId());

        // Assert
        assertThat(path).hasSize(2);

        assertThat(path.get(0).getCategory()).isEqualTo(c2.getCategory());
        assertThat(path.get(1).getCategory()).isEqualTo(c1.getCategory());
    }

    @Test
    @DisplayName("Should find the path to root")
    void testFindCategoryPathToRoot() {
        // Arrange
        Category c1 = categoryRepository.save(new Category("Europa", 0L));
        Category c2 = categoryRepository.save(new Category("Deutschland", c1.getId()));
        categoryRepository.save(new Category("DDR", c2.getId()));

        // Act
        List<Category> path = categoryRepository.findCategoryPath(c1.getId());

        // Assert
        assertThat(path).hasSize(1);

        assertThat(path.get(0).getCategory()).isEqualTo(c1.getCategory());
    }

    @Test
    @DisplayName("Should find nothing when looking for not existing category")
    void testFindNotExistingCategory() {
        // Arrange
        Category c1 = categoryRepository.save(new Category("Europa", 0L));
        Category c2 = categoryRepository.save(new Category("Deutschland", c1.getId()));
        categoryRepository.save(new Category("DDR", c2.getId()));

        // Act
        List<Category> path = categoryRepository.findCategoryPath(0L);

        // Assert
        assertThat(path).isEmpty();
    }

    @Test
    @DisplayName("Should not find categories from a different tree")
    void testFindPathInParallelTrees() {
        // Arrange
        Category root1 = categoryRepository.save(new Category("Nordamerika", null));
        Category child1 = categoryRepository.save(new Category("USA", root1.getId()));

        Category root2 = categoryRepository.save(new Category("Europa", null));
        Category child2 = categoryRepository.save(new Category("Deutschland", root2.getId()));

        // Act
        List<Category> path = categoryRepository.findCategoryPath(child2.getId());

        // Assert
        assertThat(path).hasSize(2);

        assertThat(path.get(0).getCategory()).isEqualTo(child2.getCategory());
        assertThat(path.get(1).getCategory()).isEqualTo(root2.getCategory());
    }

    /// findAllChildIds

    @Test
    @DisplayName("Should find parent and direct children")
    void testFindDirectChildren() {
        // Arrange
        Category c1 = categoryRepository.save(new Category("Europa", 0L));
        categoryRepository.save(new Category("Schweiz", c1.getId()));
        Category c2 = categoryRepository.save(new Category("Deutschland", c1.getId()));
        Category c3 = categoryRepository.save(new Category("DDR", c2.getId()));
        Category c4 = categoryRepository.save(new Category("BRD", c2.getId()));

        // Act
        List<Long> ids = categoryRepository.findAllChildIds(c2.getId());

        // Assert
        assertThat(ids).hasSize(3);

        assertThat(ids).containsAll(List.of(c2.getId(), c3.getId(), c4.getId()));
    }

    @Test
    @DisplayName("Should return only its own ID when category is a leaf")
    void testFindAllChildIdsForLeaf() {
        // Arrange
        Category root = categoryRepository.save(new Category("Europa", null));
        Category leaf = categoryRepository.save(new Category("Deutschland", root.getId()));

        // Act
        List<Long> ids = categoryRepository.findAllChildIds(leaf.getId());

        // Assert
        assertThat(ids).hasSize(1);
        assertThat(ids).contains(leaf.getId());
    }
}