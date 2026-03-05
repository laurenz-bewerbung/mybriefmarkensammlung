package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Collection;
import de.lm.mybriefmarkensammlung.domain.model.Image;
import de.lm.mybriefmarkensammlung.repository.CollectionRepository;
import de.lm.mybriefmarkensammlung.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;
    private ImageRepository imageRepository;

    public CollectionService(CollectionRepository collectionRepository, ImageRepository imageRepository) {
        this. collectionRepository = collectionRepository;
        this.imageRepository = imageRepository;
    }

    public void addCollection(String category, MultipartFile[] files, String description) throws IOException {
        List<Image> images = new ArrayList<>();

        for(MultipartFile file : files) {
            Image img = new Image(file.getOriginalFilename(), file.getBytes());
            //img = imageRepository.save(img);
            images.add(img);
        }

        Collection collection = new Collection(category, description, images);
        collectionRepository.save(collection);
    }
}
