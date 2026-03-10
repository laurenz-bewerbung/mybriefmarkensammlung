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

    public Boolean getIsExhibition() {
        return isExhibition;
    }

    public void setIsExhibition(Boolean isExhibition) {
        this.isExhibition = isExhibition;
    }

    public ExhibitionClass getExhibitionClass() {
        return exhibitionClass;
    }

    public void setExhibitionClass(ExhibitionClass exhibitionClass) {
        this.exhibitionClass = exhibitionClass;
    }
}
