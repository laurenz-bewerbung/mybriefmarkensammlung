package de.lm.mybriefmarkensammlung.dto;

import java.util.List;

public record CategoryTreeDTO (CategoryDTO category, List<CategoryTreeDTO> children) {
}