package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.*;
import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.dto.CollectionDTO;
import de.lm.mybriefmarkensammlung.repository.CategoryRepository;
import de.lm.mybriefmarkensammlung.repository.CollectionRepository;
import de.lm.mybriefmarkensammlung.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;
    private CategoryService categoryService;

    public CollectionService(CollectionRepository collectionRepository, CategoryService categoryService) {
        this. collectionRepository = collectionRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public void addCollection(String title, Long categoryId, Long[] imageIds, String description, Boolean isExhibition, ExhibitionClass exhibitionClass) {

        Set<CollectionImage> images = new HashSet<>();
        for(int i = 0; i < imageIds.length; i++) {
            images.add(new CollectionImage(imageIds[i], i));
        }

        Collection collection = new Collection(title, categoryId, description, images, isExhibition, exhibitionClass.getDisplayName());
        collectionRepository.save(collection);
    }

    public CollectionDTO getCollection(Long id) {
        Collection collection = collectionRepository.findById(id).orElse(new Collection("Sammlung konnte nicht gefunden werden", -1L, "", new HashSet<>(), false, ""));

        CollectionDTO collectionDTO = new CollectionDTO(collection.getId(),
                                                        collection.getTitle(),
                                                        categoryService.getCategoryList(collection.getCategoryId()),
                                                        collection.getDescription(),
                                                        collection.getImages().stream().sorted(Comparator.comparingInt(CollectionImage::getOrderId)).toList(),
                                                        collection.getExhibition(),
                                                        collection.getExhibitionClass());

        return collectionDTO;
    }

    public List<CollectionDTO> getCollections(Optional<String> title, Optional<Long> categoryId, Optional<Boolean> isExhibition, Optional<ExhibitionClass> exhibitionClass) {
        String exhibition = null;
        if(!exhibitionClass.isEmpty()) {
            exhibition = exhibitionClass.get().getDisplayName();
        }

        List<Collection> collections = collectionRepository.search(title.orElse(null), categoryId.orElse(null), isExhibition.orElse(null), exhibition);

        List<CollectionDTO> collectionDTOS = new ArrayList<>();
        for (Collection c : collections) {
            CollectionDTO collectionDTO = new CollectionDTO(c.getId(),
                                                            c.getTitle(),
                                                            categoryService.getCategoryList(c.getCategoryId()),
                                                            c.getDescription(),
                                                            c.getImages().stream().sorted(Comparator.comparingInt(CollectionImage::getOrderId)).toList(),
                                                            c.getExhibition(),
                                                            c.getExhibitionClass());
            collectionDTOS.add(collectionDTO);
        }

        return collectionDTOS;
    }
}