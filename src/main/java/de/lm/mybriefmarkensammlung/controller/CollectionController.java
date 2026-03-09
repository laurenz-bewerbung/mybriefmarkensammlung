package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.domain.model.ExhibitionClass;
import de.lm.mybriefmarkensammlung.service.CategoryService;
import de.lm.mybriefmarkensammlung.service.CollectionService;
import de.lm.mybriefmarkensammlung.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller()
public class CollectionController {

    private CollectionService collectionService;
    private ImageService imageService;
    private CategoryService categoryService;

    public CollectionController(CollectionService collectionService, ImageService imageService, CategoryService categoryService) {
        this.collectionService = collectionService;
        this.imageService = imageService;
        this.categoryService = categoryService;
    }


    @GetMapping("/sammlungen")
    public String overview(Model model,
                           @RequestParam("title") Optional<String> title,
                           @RequestParam("category") Optional<Long> categoryId,
                           @RequestParam("isExhibition") Optional<Boolean> isExhibition,
                           @RequestParam("exhibitionClass") Optional<ExhibitionClass> exhibitionClass) {

        model.addAttribute("collections", collectionService.getCollections(title, categoryId, isExhibition, exhibitionClass));
        model.addAttribute("categories", categoryService.getCategoryTree());
        model.addAttribute("exhibitionClasses", ExhibitionClass.values());
        return "sammlungen/overview";
    }

    @GetMapping("/sammlungen/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        model.addAttribute("collection", collectionService.getCollection(id));
        return "sammlungen/details";
    }

    @GetMapping("/sammlungen/add")
    public String add(Model model) {
        model.addAttribute("categories", categoryService.getCategoryTree());
        model.addAttribute("exhibitionClasses", ExhibitionClass.values());
        return "sammlungen/add";
    }

    @PostMapping("/sammlungen/add")
    public String add_form(@RequestParam("title") String title,
                           @RequestParam("category") Long categoryId,
                           @RequestParam("images") MultipartFile[] images,
                           @RequestParam("description") String description,
                           @RequestParam(value = "isExhibition", defaultValue = "false") boolean isExhibition,
                           @RequestParam(value = "exhibitionClass", required = false) ExhibitionClass exhibitionClass) throws IOException {

        Long[] imageIds = new Long[images.length];
        for(int i = 0; i < images.length; i++) {
            Long id = imageService.storeImage(images[i]);
            imageIds[i] = id;
        }

        collectionService.addCollection(title, categoryId, imageIds, description, isExhibition, exhibitionClass);

        return "redirect:/sammlungen";
    }
}
