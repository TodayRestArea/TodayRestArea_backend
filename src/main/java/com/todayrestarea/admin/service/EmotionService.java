package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.entity.EmotionEntity;

import java.util.List;
import java.util.Optional;

public interface EmotionService {
    //EMOTION service
    List<EmotionEntity> findEmotions();
    Long saveEmotion(EmotionEntity emotionEntity);
    void deleteEmotion(Long idx);
    Optional<EmotionEntity> findEmotionByName(String name);
}
