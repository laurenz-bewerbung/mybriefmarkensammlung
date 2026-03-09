package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.dto.CategoryDTO;
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
            map.put(c.getId(), new CategoryTreeDTO(new CategoryDTO(c.getId(), c.getCategory()), new ArrayList<>()));
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

    public List<CategoryDTO> getCategoryList(Long categoryId) {

        List<CategoryDTO> categoryList = new ArrayList<>();
        while(categoryId != 1) {
            Category currentHead = categoryRepository.findById(categoryId).orElseThrow();
            categoryList.addFirst(new CategoryDTO(currentHead.getId(), currentHead.getCategory()));
            categoryId = currentHead.getParentId();
        }

        return categoryList;
    }

    public List<Long> getAllChildIds(Long categoryId) {
        List<CategoryDTO> categoryDTOS = getCategoryList(categoryId);
        List<CategoryTreeDTO> categoryTree = getCategoryTree();

        for(CategoryDTO c : categoryDTOS) {
            categoryTree = categoryTree.stream().filter(x -> x.category().id() == c.id()).findFirst().orElseThrow().children();
        }

        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(categoryId);

        getAllChildIds(categoryTree, categoryIds);

        return categoryIds;
    }

    private void getAllChildIds(List<CategoryTreeDTO> categoryTree, List<Long> categoryIds) {
        for (CategoryTreeDTO c : categoryTree) {
            categoryIds.add(c.category().id());
            getAllChildIds(c.children(), categoryIds);
        }
    }
}