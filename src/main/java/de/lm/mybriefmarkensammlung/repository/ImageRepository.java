package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
