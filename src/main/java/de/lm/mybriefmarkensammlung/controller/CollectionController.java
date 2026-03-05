package de.lm.mybriefmarkensammlung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller()
public class CollectionController {

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
                           @RequestParam("description") String description) {

        System.out.println(images.length);

        return "sammlungen/add";
    }
}
