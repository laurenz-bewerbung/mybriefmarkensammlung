package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.domain.model.CollectionImage;
import de.lm.mybriefmarkensammlung.domain.model.Image;
import de.lm.mybriefmarkensammlung.repository.CollectionRepository;
import de.lm.mybriefmarkensammlung.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this. collectionRepository = collectionRepository;
    }

    @Transactional
    public void addCollection(String category, Long[] imageIds, String description) {

        Set<CollectionImage> images = new HashSet<>();
        for(int i = 0; i < imageIds.length; i++) {
            images.add(new CollectionImage(imageIds[i], i));
        }

        Collection collection = new Collection(category, description, images);
        collectionRepository.save(collection);
    }

    public Collection getCollection(Long id) {
        Optional<Collection> optCollection = collectionRepository.findById(id);

        if(optCollection.isEmpty()) {
            // todo: empty Collection
        }

        return optCollection.get();
    }

    public List<Collection> getCollections() {
        return (List<Collection>) collectionRepository.findAll();
    }
}