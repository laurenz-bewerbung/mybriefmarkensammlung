package de.lm.mybriefmarkensammlung.repository;

import de.lm.mybriefmarkensammlung.domain.model.Category;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query(value = """
    WITH RECURSIVE category_path AS (
        SELECT id, category, parent_id
        FROM category
        WHERE id = :categoryId
        
        UNION ALL
        
        SELECT c.id, c.category, c.parent_id
        FROM category c
        JOIN category_path cp ON cp.parent_id = c.id
    )
    SELECT * FROM category_path
    """)
    List<Category> findCategoryPath(@Param("categoryId") Long categoryId);


    @Query(value = """
    WITH RECURSIVE sub_categories AS (
        SELECT id FROM category WHERE id = :categoryId
        
        UNION ALL
        
        SELECT c.id FROM category c
        JOIN sub_categories sc ON c.parent_id = sc.id
    )
    SELECT id FROM sub_categories
    """)
    List<Long> findAllChildIds(@Param("categoryId") Long categoryId);
}
