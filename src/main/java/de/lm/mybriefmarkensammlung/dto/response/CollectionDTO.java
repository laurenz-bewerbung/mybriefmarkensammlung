package de.lm.mybriefmarkensammlung.dto.response;

import de.lm.mybriefmarkensammlung.domain.model.CollectionImage;
import de.lm.mybriefmarkensammlung.domain.model.ExhibitionClass;

import java.util.List;


public record CollectionDTO(Long id, String title, List<CategoryDTO> categoryList, String description, List<CollectionImage> images, Boolean isExhibition, ExhibitionClass exhibitionClass, String username) {
}
