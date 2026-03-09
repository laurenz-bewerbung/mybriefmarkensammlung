package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Collection;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CollectionRepository extends CrudRepository<Collection, Long> {

    @Query("""
        SELECT * FROM collection 
        WHERE (:title IS NULL OR title LIKE CONCAT('%', :title, '%'))
          AND (:categoryId IS NULL OR category_id = :categoryId)
          AND (:isExhibition IS NULL OR is_exhibition = :isExhibition)
          AND (:exClass IS NULL OR exhibition_class = :exClass)
    """)
    List<Collection> search(
            String title,
            Long categoryId,
            Boolean isExhibition,
            String exClass
    );
}
