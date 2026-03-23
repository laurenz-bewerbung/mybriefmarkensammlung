package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.dto.request.CategoryCreateRequest;
import de.lm.mybriefmarkensammlung.service.CategoryService;
import jakarta.validation.Valid;
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
    public String addCategory(@Valid  CategoryCreateRequest createRequest) {

        categoryService.addCategory(createRequest);
        return "redirect:/categories";
    }
}
