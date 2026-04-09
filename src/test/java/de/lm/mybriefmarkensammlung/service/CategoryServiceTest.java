package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.dto.request.CategoryCreateRequest;
import de.lm.mybriefmarkensammlung.exception.NoSuchCategoryException;
import de.lm.mybriefmarkensammlung.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("addCategory should save valid requests to db")
    void testAddCategorySuccess() {
        // Arrange
        CategoryCreateRequest request = new CategoryCreateRequest("DDR", 99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.of(new Category("Deutschland", 0L)));
        when(categoryRepository.save(any(Category.class))).thenAnswer(e -> e.getArguments()[0]);

        // Act
        Category res = categoryService.addCategory(request);

        // Assert
        assertThat(res.getCategory()).isEqualTo(request.getCategory());
        verify(categoryRepository).save(res);
    }

    @Test
    @DisplayName("addCategory should save root category")
    void testAddCategoryRoot() {
        // Arrange
        CategoryCreateRequest request = new CategoryCreateRequest("Deutschland", null);
        when(categoryRepository.save(any(Category.class))).thenAnswer(e -> e.getArguments()[0]);

        // Act
        Category res = categoryService.addCategory(request);

        // Assert
        assertThat(res.getCategory()).isEqualTo(request.getCategory());
        verify(categoryRepository).save(res);
    }

    @Test
    @DisplayName("addCategory should throw exception when parent not found")
    void testAddCategoryShouldThrowExceptionWhenParentNotFound() {
        // Arrange
        CategoryCreateRequest request = new CategoryCreateRequest("DDR", 99L);
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchCategoryException.class, () -> categoryService.addCategory(request));
        verify(categoryRepository, never()).save(any());
    }
}