package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.*;
import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.dto.request.CollectionCreateRequest;
import de.lm.mybriefmarkensammlung.dto.request.CollectionSearchRequest;
import de.lm.mybriefmarkensammlung.dto.response.CollectionDTO;
import de.lm.mybriefmarkensammlung.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;
    private CategoryService categoryService;
    private ImageService imageService;

    public CollectionService(CollectionRepository collectionRepository, CategoryService categoryService, ImageService imageService) {
        this. collectionRepository = collectionRepository;
        this.categoryService = categoryService;
        this.imageService = imageService;
    }

    @Transactional
    public void addCollection(CollectionCreateRequest createRequest) throws IOException {
        System.out.println(createRequest.getIsExhibition());
        System.out.println(createRequest.getExhibitionClass());
        Long[] imageIds = imageService.storeImages(createRequest.getImages());

        Set<CollectionImage> images = new HashSet<>();
        for(int i = 0; i < imageIds.length; i++) {
            images.add(new CollectionImage(imageIds[i], i));
        }

        Collection collection = new Collection( createRequest.getTitle(),
                                                createRequest.getCategory(),
                                                createRequest.getDescription(),
                                                images,
                                                createRequest.getIsExhibition(),
                                                createRequest.getExhibitionClass() != null ? createRequest.getExhibitionClass().name() : null);
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
                                    entity.getExhibition() ? ExhibitionClass.valueOf(entity.getExhibitionClass()).getDisplayName() : null);
    }
}