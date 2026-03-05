package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Collection;
import org.springframework.data.repository.CrudRepository;

public interface CollectionRepository extends CrudRepository<Collection, Long> {
}
