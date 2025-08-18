package com.travel.blog.repository;

import com.travel.blog.entity.Trip;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);

    Optional<Trip> findByIdAndIsDeletedFalse(Long id);

    boolean existsBySlugAndIsDeletedFalse(String slug);
}
