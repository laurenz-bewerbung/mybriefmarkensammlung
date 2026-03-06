package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.service.CollectionService;
import de.lm.mybriefmarkensammlung.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller()
public class CollectionController {

    private CollectionService collectionService;
    private ImageService imageService;

    public CollectionController(CollectionService collectionService, ImageService imageService) {
        this.collectionService = collectionService;
        this.imageService = imageService;
    }


    @GetMapping("/sammlungen")
    public String overview(Model model) {
        model.addAttribute("collections", collectionService.getCollections());
        return "sammlungen/overview";
    }

    @GetMapping("/sammlungen/add")
    public String add() {
        return "sammlungen/add";
    }

    @PostMapping("/sammlungen/add")
    public String add_form(@RequestParam("category") String category,
                           @RequestParam("images") MultipartFile[] images,
                           @RequestParam("description") String description) throws IOException {

        Long[] imageIds = new Long[images.length];
        for(int i = 0; i < images.length; i++) {
            Long id = imageService.storeImage(images[i]);
            imageIds[i] = id;
        }

        collectionService.addCollection(category, imageIds, description);

        return "redirect:/sammlungen";
    }
}
