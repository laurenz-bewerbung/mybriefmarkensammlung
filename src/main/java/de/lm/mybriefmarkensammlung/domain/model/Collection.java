package de.lm.mybriefmarkensammlung.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("collection")
public class Collection {

    @Id
    private Long id;

    private String category;
    private String description;

    @MappedCollection(idColumn = "collection_id", keyColumn = "order_index")
    private List<Image> images;

    public Collection() {}

    public Collection(String category, String description, List<Image> images) {
        this.category = category;
        this.description = description;
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}