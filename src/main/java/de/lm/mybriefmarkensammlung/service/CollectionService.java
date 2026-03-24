package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.*;
import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.dto.request.CollectionCreateRequest;
import de.lm.mybriefmarkensammlung.dto.request.CollectionEditRequest;
import de.lm.mybriefmarkensammlung.dto.request.CollectionSearchRequest;
import de.lm.mybriefmarkensammlung.dto.response.CollectionDTO;
import de.lm.mybriefmarkensammlung.exception.NoSuchCollectionException;
import de.lm.mybriefmarkensammlung.exception.OwnershipException;
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
    private UserService userService;

    public CollectionService(CollectionRepository collectionRepository, CategoryService categoryService, ImageService imageService, UserService userService) {
        this. collectionRepository = collectionRepository;
        this.categoryService = categoryService;
        this.imageService = imageService;
        this.userService = userService;
    }

    @Transactional
    public void addCollection(CollectionCreateRequest createRequest, Long userId) throws IOException {
        // save images
        Long[] imageIds = imageService.storeImages(createRequest.getImages());

        // instantiate collection_images
        Set<CollectionImage> images = new HashSet<>();
        for(int i = 0; i < imageIds.length; i++) {
            images.add(new CollectionImage(imageIds[i], i));
        }

        // parse createRequest to Collection and store in db
        Collection collection = new Collection( createRequest.getTitle(),
                                                createRequest.getCategory(),
                                                createRequest.getDescription(),
                                                images,
                                                createRequest.getIsExhibition(),
                                                createRequest.getExhibitionClass() != null ? createRequest.getExhibitionClass().name() : null,
                                                userId);
        collectionRepository.save(collection);
    }

    public void editCollection(CollectionEditRequest editRequest, Long collectionId, Long userId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new NoSuchCollectionException(collectionId));

        if(!collection.getUserId().equals(userId)) {
            throw new OwnershipException();
        }

        collection.setTitle(editRequest.getTitle());
        collection.setCategoryId(editRequest.getCategory());
        collection.setDescription(editRequest.getDescription());
        collection.setExhibition(editRequest.getIsExhibition());
        collection.setExhibitionClass(editRequest.getExhibitionClass() != null ? editRequest.getExhibitionClass().name() : null);

        collectionRepository.save(collection);
    }

    public CollectionDTO getCollection(Long id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> new NoSuchCollectionException(id));
        return entityToDto(collection);
    }

    public List<CollectionDTO> getCollections(CollectionSearchRequest searchRequest) {
        // handle search for username
        Long userId = null;
        if (searchRequest.getUsername() != null && !searchRequest.getUsername().isBlank()) {
            userId = userService.userIdByUsername(searchRequest.getUsername(), false);

            if (userId == null) {
                return new ArrayList<>(); // User does not exist -> no search results
            }
        }

        // filter collections
        List<Collection> collections = collectionRepository.search(
                searchRequest.getTitle(),
                searchRequest.getCategory() != null ? categoryService.getAllChildIds(searchRequest.getCategory()).toArray(new Long[0]) : null,
                searchRequest.getIsExhibition(),
                searchRequest.getExhibitionClass() != null ? searchRequest.getExhibitionClass().name() : null,
                userId
        );

        // map collections to DTOs
        return collections.stream().map(c -> entityToDto(c)).toList();
    }

    private CollectionDTO entityToDto(Collection entity) {
        return new CollectionDTO(   entity.getId(),
                                    entity.getTitle(),
                                    categoryService.getCategoryPath(entity.getCategoryId()),
                                    entity.getDescription(),
                                    entity.getImages().stream().sorted(Comparator.comparingInt(CollectionImage::getOrderId)).toList(),
                                    entity.getExhibition(),
                                    entity.getExhibition() ? ExhibitionClass.valueOf(entity.getExhibitionClass()).getDisplayName() : null,
                                    userService.usernameByUserId(entity.getUserId(), false));
    }
}