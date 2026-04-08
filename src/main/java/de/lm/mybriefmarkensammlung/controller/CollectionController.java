package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.domain.model.ExhibitionClass;
import de.lm.mybriefmarkensammlung.dto.request.CollectionCreateRequest;
import de.lm.mybriefmarkensammlung.dto.request.CollectionEditRequest;
import de.lm.mybriefmarkensammlung.dto.request.CollectionSearchRequest;
import de.lm.mybriefmarkensammlung.dto.response.CollectionDTO;
import de.lm.mybriefmarkensammlung.service.CategoryService;
import de.lm.mybriefmarkensammlung.service.CollectionService;
import de.lm.mybriefmarkensammlung.service.ImageService;
import de.lm.mybriefmarkensammlung.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Controller()
public class CollectionController {

    private CollectionService collectionService;
    private ImageService imageService;
    private CategoryService categoryService;
    private UserService userService;

    public CollectionController(CollectionService collectionService, ImageService imageService, CategoryService categoryService, UserService userService) {
        this.collectionService = collectionService;
        this.imageService = imageService;
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @GetMapping("/sammlungen")
    public String overview(Model model, @Valid CollectionSearchRequest searchRequest) {

        model.addAttribute("collections", collectionService.getCollections(searchRequest));
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
        model.addAttribute("collectionCreateRequest", new CollectionCreateRequest());
        return "sammlungen/add";
    }

    @PostMapping("/sammlungen/add")
    public String add_form(@Valid @ModelAttribute CollectionCreateRequest createRequest, BindingResult bindingResult, Principal principal, Model model) throws IOException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoryTree());
            model.addAttribute("exhibitionClasses", ExhibitionClass.values());
            return "sammlungen/add";
        }

        Long userId = userService.userIdByUsername(principal.getName(), true);
        collectionService.addCollection(createRequest, userId);
        return "redirect:/sammlungen";
    }

    @GetMapping("/sammlungen/edit/{id}")
    public String edit(Model model, Principal principal, @PathVariable("id") Long collectionId) {
        Long userId = userService.userIdByUsername(principal.getName(), true);
        collectionService.handleIllegalRessourceRequest(collectionId, userId);

        model.addAttribute("categories", categoryService.getCategoryTree());
        model.addAttribute("exhibitionClasses", ExhibitionClass.values());
        model.addAttribute("collection", collectionService.getCollection(collectionId));
        model.addAttribute("collectionEditRequest", new CollectionEditRequest());
        return "sammlungen/edit";
    }

    @PostMapping("/sammlungen/edit/{id}")
    public String edit_form(@Valid @ModelAttribute("collectionEditRequest") CollectionEditRequest editRequest, BindingResult bindingResult, @PathVariable("id") Long collectionId, Principal principal, Model model) throws IOException {
        Long userId = userService.userIdByUsername(principal.getName(), true);
        collectionService.handleIllegalRessourceRequest(collectionId, userId);

        // validate required image
        if((editRequest.getExistingImageIds() == null || editRequest.getExistingImageIds().size() == 0) && (editRequest.getNewImages() == null || editRequest.getNewImages().length == 0 || editRequest.getNewImages()[0].isEmpty())) {
            bindingResult.rejectValue("existingImageIds", "error","Die Sammlung braucht mindestens ein Bild.");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoryTree());
            model.addAttribute("exhibitionClasses", ExhibitionClass.values());
            model.addAttribute("collection", collectionService.getCollection(collectionId));
            return "sammlungen/edit";
        }

        collectionService.editCollection(editRequest, collectionId);
        return "redirect:/sammlungen/" + collectionId;
    }

    @PostMapping("/sammlungen/delete/{id}")
    public String delete(@PathVariable("id") Long collectionId, Principal principal) {
        Long userId = userService.userIdByUsername(principal.getName(), true);
        collectionService.handleIllegalRessourceRequest(collectionId, userId);

        collectionService.deleteCollection(collectionId);

        return "redirect:/sammlungen";
    }
}
