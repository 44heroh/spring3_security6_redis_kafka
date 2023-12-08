package com.example.spring3security6docker.repository;

import com.example.spring3security6docker.dao.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.status = 'ACTIVE'")
    Page<Course> findAll(Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.author = :author AND c.name = :name AND c.status = 'ACTIVE'")
    Optional<Course> findByParams(
            @Param("author") String author,
            @Param("name") String name
    );

    @Query("SELECT c FROM Course c WHERE c.status = 'ACTIVE' ORDER BY c.id DESC LIMIT 5 OFFSET 0")
    Optional<List<Course>> findLast5();
}
