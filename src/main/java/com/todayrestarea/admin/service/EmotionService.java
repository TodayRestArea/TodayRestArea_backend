package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.entity.EmotionEntity;

import java.util.List;

public interface EmotionService {
    //EMOTION service
    List<EmotionEntity> findEmotions();
    Long saveEmotion(EmotionEntity emotionEntity);
    Long findEmotionByName(String name);
}
