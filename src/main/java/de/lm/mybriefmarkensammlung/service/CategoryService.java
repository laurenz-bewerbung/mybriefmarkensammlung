package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.dto.CategoryTreeDTO;
import de.lm.mybriefmarkensammlung.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String category, Long parentId) {
        Category cat = new Category(category, parentId);
        cat = categoryRepository.save(cat);
        return cat;
    }

    public List<CategoryTreeDTO> getCategoryTree() {
        List<Category> all = (List<Category>) categoryRepository.findAll();

        Map<Long, CategoryTreeDTO> map = new HashMap<>();
        for (Category c : all) {
            map.put(c.getId(), new CategoryTreeDTO(c, new ArrayList<>()));
        }

        List<CategoryTreeDTO> roots = new ArrayList<>();
        for (Category c : all) {
            CategoryTreeDTO node = map.get(c.getId());
            if (c.getParentId() == null) {
                roots.add(node);
            } else {
                CategoryTreeDTO parent = map.get(c.getParentId());
                if (parent != null) {
                    parent.children().add(node);
                } else {
                    roots.add(node);
                }
            }
        }
        return roots.get(0).children();
    }

    public List<Category> getCategoryList(Long categoryId) {

        List<Category> categoryList = new ArrayList<>();
        while(categoryId != 1) {
            Category currentHead = categoryRepository.findById(categoryId).orElseThrow();
            categoryList.addFirst(currentHead);
            categoryId = currentHead.getParentId();
        }

        return categoryList;
    }
}