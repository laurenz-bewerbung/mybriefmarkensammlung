package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.domain.model.CollectionImage;
import de.lm.mybriefmarkensammlung.domain.model.Image;
import de.lm.mybriefmarkensammlung.dto.CollectionDTO;
import de.lm.mybriefmarkensammlung.repository.CategoryRepository;
import de.lm.mybriefmarkensammlung.repository.CollectionRepository;
import de.lm.mybriefmarkensammlung.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void addCollection(Long categoryId, Long[] imageIds, String description) {

        Set<CollectionImage> images = new HashSet<>();
        for(int i = 0; i < imageIds.length; i++) {
            images.add(new CollectionImage(imageIds[i], i));
        }

        Collection collection = new Collection(categoryId, description, images);
        collectionRepository.save(collection);
    }

    public CollectionDTO getCollection(Long id) {
        Collection collection = collectionRepository.findById(id).orElse(new Collection(-1L, "Sammlung konnte nicht gefunden werden", new HashSet<>()));

        CollectionDTO collectionDTO = new CollectionDTO(collection.getId(),
                                                        categoryService.getCategoryList(collection.getCategoryId()),
                                                        collection.getDescription(),
                                                        collection.getImages().stream().sorted(Comparator.comparingInt(CollectionImage::getOrderId)).toList());

        return collectionDTO;
    }

    public List<CollectionDTO> getCollections() {
        List<Collection> collections = (List<Collection>) collectionRepository.findAll();

        List<CollectionDTO> collectionDTOS = new ArrayList<>();
        for (Collection c : collections) {
            CollectionDTO collectionDTO = new CollectionDTO(c.getId(),
                                                            categoryService.getCategoryList(c.getCategoryId()),
                                                            c.getDescription(),
                                                            c.getImages().stream().sorted(Comparator.comparingInt(CollectionImage::getOrderId)).toList());
            collectionDTOS.add(collectionDTO);
        }

        return collectionDTOS;
    }
}