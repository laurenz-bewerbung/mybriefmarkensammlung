package de.lm.mybriefmarkensammlung.dto;

import de.lm.mybriefmarkensammlung.domain.model.Category;

import java.util.List;

public record CategoryTreeDTO (Category category, List<CategoryTreeDTO> children) {
}