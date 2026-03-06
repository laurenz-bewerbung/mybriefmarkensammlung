package de.lm.mybriefmarkensammlung.domain.model;

import org.springframework.data.relational.core.mapping.Table;

@Table("collection_image")
public class CollectionImage {

    private Long imageId;
    private Integer orderId;

    public CollectionImage() {}

    public CollectionImage(Long imageId, Integer orderId) {
        this.imageId = imageId;
        this.orderId = orderId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}