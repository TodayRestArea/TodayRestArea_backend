package com.todayrestarea.admin.repository;

import com.todayrestarea.admin.model.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaMusicRepository extends JpaRepository<MusicEntity,Long> {
    Optional<MusicEntity> checkExistence(@Param("title")String title, @Param("artist")String artist);
}
