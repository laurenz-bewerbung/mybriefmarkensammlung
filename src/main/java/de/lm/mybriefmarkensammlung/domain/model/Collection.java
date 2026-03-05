package de.lm.mybriefmarkensammlung.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Table("collection")
public class Collection {

    @Id
    private Long id;

    private String category;
    private String description;
    private List<Long> imageIds;

    public Collection() {}

    public Collection(String category, String description, List<Long> imageIds) {
        this.category = category;
        this.description = description;
        this.imageIds = imageIds;
    }

    public void addImage(Long imageId) {
        imageIds.add(imageId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImages(List<Long> imageIds) {
        this.imageIds = imageIds;
    }
}