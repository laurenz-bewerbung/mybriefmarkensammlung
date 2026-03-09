package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategoryTree());
        return "categories/overview";
    }

    @PostMapping("/categories")
    @ResponseBody
    public ResponseEntity<Category> addCategoryApi(
            @RequestParam("category") String name,
            @RequestParam(value = "parentId", required = false) Long parentId) {

        Category newCat = categoryService.addCategory(name, parentId);
        return ResponseEntity.ok(newCat);
    }
}
