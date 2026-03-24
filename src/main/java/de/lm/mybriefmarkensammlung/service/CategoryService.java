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
        // load all categories from db
        Iterable<Category> allCategories = categoryRepository.findAll();

        // map: id -> List<CategoryTreeDTO> to find in O(1)
        Map<Long, CategoryTreeDTO> map = new HashMap<>();
        for (Category c : allCategories) {
            map.put(c.getId(), new CategoryTreeDTO(new CategoryDTO(c.getId(), c.getCategory()), new ArrayList<>()));
        }

        // build tree iterativ
        List<CategoryTreeDTO> roots = new ArrayList<>();
        for (Category c : allCategories) {
            CategoryTreeDTO node = map.get(c.getId());

            if (c.getParentId() == null) {
                roots.add(node);
            } else {
                if (!map.containsKey(c.getParentId())) throw new NoSuchCategoryException();

                CategoryTreeDTO parent = map.get(c.getParentId());
                parent.children().add(node);
            }
        }

        return roots;
    }

    public List<CategoryDTO> getCategoryPath(Long categoryId) {
        List<Category> reversedCategoryPath = categoryRepository.findCategoryPathNative(categoryId);

        if(reversedCategoryPath == null || reversedCategoryPath.size() == 0) {
            throw new NoSuchCategoryException();
        }

        List<CategoryDTO> categoryPath = new LinkedList<>();
        for(Category c : reversedCategoryPath) {
            categoryPath.addFirst(new CategoryDTO(c.getId(), c.getCategory()));
        }

        return categoryPath;
    }

    public List<Long> getAllChildIds(Long categoryId) {
        List<CategoryDTO> categoryDTOS = getCategoryPath(categoryId);
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