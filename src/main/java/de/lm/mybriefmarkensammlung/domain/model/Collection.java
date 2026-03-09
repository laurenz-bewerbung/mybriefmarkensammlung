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

    private String title;
    private Long categoryId;
    private Boolean isExhibition;
    private String exhibitionClass;
    private String description;

    @MappedCollection(idColumn = "collection_id", keyColumn = "image_id")
    private Set<CollectionImage> images = new HashSet<>();

    public Collection() {}

    public Collection(String title, Long categoryId, String description, Set<CollectionImage> images, Boolean isExhibition, String exhibitionClass) {
        this.title = title;
        this.categoryId = categoryId;
        this.isExhibition = isExhibition;
        this.exhibitionClass = exhibitionClass;
        this.description = description;
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

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getExhibition() {
        return isExhibition;
    }

    public void setExhibition(Boolean exhibition) {
        isExhibition = exhibition;
    }

    public String getExhibitionClass() {
        return exhibitionClass;
    }

    public void setExhibitionClass(String exhibitionClass) {
        this.exhibitionClass = exhibitionClass;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}