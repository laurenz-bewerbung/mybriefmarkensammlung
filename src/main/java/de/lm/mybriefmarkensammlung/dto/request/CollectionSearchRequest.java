package de.lm.mybriefmarkensammlung.dto.request;

import de.lm.mybriefmarkensammlung.domain.model.ExhibitionClass;

public class CollectionSearchRequest {
    private String title;
    private Long category;
    private Boolean isExhibition;
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

    public void setCategory(Long categoryId) {
        this.category = categoryId;
    }

    public Boolean getExhibition() {
        return isExhibition;
    }

    public void setExhibition(Boolean exhibition) {
        isExhibition = exhibition;
    }

    public ExhibitionClass getExhibitionClass() {
        return exhibitionClass;
    }

    public void setExhibitionClass(ExhibitionClass exhibitionClass) {
        this.exhibitionClass = exhibitionClass;
    }
}
