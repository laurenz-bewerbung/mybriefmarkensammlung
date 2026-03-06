package de.lm.mybriefmarkensammlung.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("image")
public class Image {

    @Id
    private Long id;
    private String filename;
    private byte[] content;

    public Image() {}

    public Image(String filename, byte[] content, Integer orderId) {
        this.filename = filename;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}