package de.lm.mybriefmarkensammlung.dto.response;

import java.util.List;

public record CategoryTreeDTO (CategoryDTO category, List<CategoryTreeDTO> children) {
}