package de.lm.mybriefmarkensammlung.dto.request;

import de.lm.mybriefmarkensammlung.domain.model.ExhibitionClass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CollectionEditRequest {

    @NotBlank(message = "Gebe einen Titel an.")
    private String title;
    @NotNull(message = "Wähle eine Kategorie aus.")
    private Long category;

    List<Long> existingImageIds;
    MultipartFile[] newImages;

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
