package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.*;
import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.dto.request.CollectionSearchRequest;
import de.lm.mybriefmarkensammlung.dto.response.CollectionDTO;
import de.lm.mybriefmarkensammlung.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void addCollection(String title, Long categoryId, Long[] imageIds, String description, Boolean isExhibition, Optional<ExhibitionClass> exhibitionClass) {

        Set<CollectionImage> images = new HashSet<>();
        for(int i = 0; i < imageIds.length; i++) {
            images.add(new CollectionImage(imageIds[i], i));
        }

        Collection collection = new Collection(title, categoryId, description, images, isExhibition, exhibitionClass.isPresent() ? exhibitionClass.get().getDisplayName() : null);
        collectionRepository.save(collection);
    }

    public CollectionDTO getCollection(Long id) {
        Collection collection = collectionRepository.findById(id).orElse(new Collection("Sammlung konnte nicht gefunden werden", -1L, "", new HashSet<>(), false, ""));

        CollectionDTO collectionDTO = entityToDto(collection);

        return collectionDTO;
    }

    public List<CollectionDTO> getCollections(CollectionSearchRequest searchRequest) {
        List<Collection> collections = collectionRepository.search(
                searchRequest.getTitle(),
                searchRequest.getCategory() != null ? categoryService.getAllChildIds(searchRequest.getCategory()).toArray(new Long[0]) : null,
                searchRequest.getExhibition(),
                searchRequest.getExhibitionClass() != null ? searchRequest.getExhibitionClass().getDisplayName() : null
        );

        List<CollectionDTO> collectionDTOS = new ArrayList<>();
        for (Collection c : collections) {
            collectionDTOS.add(entityToDto(c));
        }

        return collectionDTOS;
    }

    private CollectionDTO entityToDto(Collection entity) {
        return new CollectionDTO(   entity.getId(),
                                    entity.getTitle(),
                                    categoryService.getCategoryList(entity.getCategoryId()),
                                    entity.getDescription(),
                                    entity.getImages().stream().sorted(Comparator.comparingInt(CollectionImage::getOrderId)).toList(),
                                    entity.getExhibition(),
                                    entity.getExhibitionClass());
    }
}