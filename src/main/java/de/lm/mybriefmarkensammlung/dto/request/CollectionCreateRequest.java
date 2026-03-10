package de.lm.mybriefmarkensammlung.dto.request;

import de.lm.mybriefmarkensammlung.domain.model.ExhibitionClass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;

public class CollectionCreateRequest {

    @NotBlank(message = "Gebe einen Titel an.")
    private String title;
    @NotNull(message = "Wähle eine Kategorie aus.")
    private Long category;
    @NotEmpty(message = "Lade mindestens ein Bild hoch.")
    private MultipartFile[] images;

    private String description;
    private boolean isExhibition;
    private ExhibitionClass exhibitionClass;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExhibitionClass getExhibitionClass() {
        return exhibitionClass;
    }

    public void setExhibitionClass(ExhibitionClass exhibitionClass) {
        this.exhibitionClass = exhibitionClass;
    }

    public boolean getIsExhibition() {
        return isExhibition;
    }

    public void setIsExhibition(boolean exhibition) {
        isExhibition = exhibition;
    }
}
