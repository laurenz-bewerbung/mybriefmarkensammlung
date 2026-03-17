package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Collection;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionRepository extends CrudRepository<Collection, Long> {

    @Query("""
        SELECT * FROM collection 
        WHERE (:title IS NULL OR title ILIKE CONCAT('%', CAST(:title AS TEXT), '%'))
          AND (CAST(:categoryIds AS BIGINT[]) IS NULL OR category_id = ANY(CAST(:categoryIds AS BIGINT[])))
          AND (:isExhibition IS NULL OR is_exhibition = :isExhibition)
          AND (:exClass IS NULL OR exhibition_class = :exClass)
          AND (:userId IS NULL OR user_id = :userId)
    """)
    List<Collection> search(
            @Param("title") String title,
            @Param("categoryIds") Long[] categoryIds,
            @Param("isExhibition") Boolean isExhibition,
            @Param("exClass") String exClass,
            @Param("userId") Long userId
    );
}
