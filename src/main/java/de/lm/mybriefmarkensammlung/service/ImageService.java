package de.lm.mybriefmarkensammlung.service;

import de.lm.mybriefmarkensammlung.domain.model.Image;
import de.lm.mybriefmarkensammlung.exception.NoSuchImageException;
import de.lm.mybriefmarkensammlung.repository.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Long[] storeImages(MultipartFile[] files) throws IOException {
        Long[] ids = new Long[files.length];
        for(int i = 0; i < files.length; i++) {
            ids[i] = storeImage(files[i], i);
        }
        return ids;
    }

    public Long storeImage(MultipartFile file, int orderId) throws IOException {
        Image image = new Image(file.getOriginalFilename(), compressImage(file), orderId);
        image = imageRepository.save(image);

        return image.getId();
    }

    public Image loadImage(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new NoSuchImageException(id));
    }

    private byte[] compressImage(MultipartFile mpFile) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(mpFile.getInputStream())
                .size(1200, 1600)
                .outputFormat("jpg")
                .outputQuality(0.75)
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }
}
