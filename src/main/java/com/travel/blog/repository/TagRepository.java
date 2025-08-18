package com.travel.blog.repository;

import com.travel.blog.entity.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    boolean existsBySlugAndNameNotIgnoreCase(String slug, String name);
    Optional<Tag> findByIdAndIsDeletedFalse(Long id);
    List<Tag> findAllByIsDeletedFalse();
}