package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller()
public class CollectionController {

    @Autowired
    CollectionService collectionService;


    @GetMapping("/sammlungen")
    public String overview() {
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

        collectionService.addCollection(category, images, description);

        return "redirect:/sammlung/add";
    }
}
