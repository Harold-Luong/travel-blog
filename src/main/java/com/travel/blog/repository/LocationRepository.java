package com.travel.blog.repository;

import com.travel.blog.entity.Location;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByIdAndIsDeletedFalse(Long id);
    List<Location> findAllByTags_SlugAndTags_IsDeletedFalse(String slug);

    Page<Location> findByTripUserId(Long userId, Pageable pageable);
}

