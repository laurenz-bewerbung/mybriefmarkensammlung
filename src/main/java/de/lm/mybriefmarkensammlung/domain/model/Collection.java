package de.lm.mybriefmarkensammlung.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("collection")
public class Collection {

    @Id
    private Long id;

    private Long categoryId;
    private String description;

    @MappedCollection(idColumn = "collection_id", keyColumn = "image_id")
    private Set<CollectionImage> images = new HashSet<>();

    public Collection() {}

    public Collection(Long categoryId, String description, Set<CollectionImage> images) {
        this.categoryId = categoryId;
        this.images = images;
    }

    public void addImage(CollectionImage image) {
        images.add(image);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategory(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CollectionImage> getImages() {
        return images;
    }

    public void setImages(Set<CollectionImage> images) {
        this.images = images;
    }
}