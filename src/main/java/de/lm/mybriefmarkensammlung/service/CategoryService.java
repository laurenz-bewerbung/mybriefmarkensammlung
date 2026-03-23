package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.dto.request.CategoryCreateRequest;
import de.lm.mybriefmarkensammlung.dto.response.CategoryDTO;
import de.lm.mybriefmarkensammlung.dto.response.CategoryTreeDTO;
import de.lm.mybriefmarkensammlung.exception.NoSuchCategoryException;
import de.lm.mybriefmarkensammlung.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(CategoryCreateRequest createRequest) {
        // error if parent category doesn't exists
        if (createRequest.getParentId() != null && categoryRepository.findById(createRequest.getParentId()).isEmpty()) {
            throw new NoSuchCategoryException();
        }

        Category cat = new Category(createRequest.getCategory(), createRequest.getParentId());
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
        return roots;
    }

    public List<CategoryDTO> getCategoryList(Long categoryId) {

        List<CategoryDTO> categoryList = new ArrayList<>();
        while(categoryId != null) {
            Category currentHead = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchCategoryException());
            categoryList.addFirst(new CategoryDTO(currentHead.getId(), currentHead.getCategory()));
            categoryId = currentHead.getParentId();
        }

        return categoryList;
    }

    public List<Long> getAllChildIds(Long categoryId) {
        List<CategoryDTO> categoryDTOS = getCategoryList(categoryId);
        List<CategoryTreeDTO> categoryTree = getCategoryTree();

        for(CategoryDTO c : categoryDTOS) {
            categoryTree = categoryTree.stream().filter(x -> x.category().id() == c.id()).findFirst().orElseThrow(() -> new NoSuchCategoryException(c.id())).children();
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