package de.lm.mybriefmarkensammlung.dto;

import de.lm.mybriefmarkensammlung.domain.model.CollectionImage;

import java.util.List;


public record CollectionDTO(Long id, List<CategoryDTO> categoryList, String description, List<CollectionImage> images) {
}
