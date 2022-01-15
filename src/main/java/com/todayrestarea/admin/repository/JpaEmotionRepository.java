package com.todayrestarea.admin.repository;

import com.todayrestarea.admin.model.entity.EmotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface JpaEmotionRepository extends JpaRepository<EmotionEntity,Long> {
    EmotionEntity findEmotionByName(@Param("emotion_name")String name);
}
