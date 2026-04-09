package de.lm.mybriefmarkensammlung.dto.request;

import jakarta.validation.constraints.NotNull;

public class CategoryCreateRequest {

    @NotNull
    private String category;
    private Long parentId;

    public CategoryCreateRequest(String category, Long parentId) {
        this.category = category;
        this.parentId = parentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
