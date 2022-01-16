package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.entity.EmotionEntity;
import com.todayrestarea.admin.repository.JpaEmotionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class EmotionServiceImpl implements EmotionService {
    final private JpaEmotionRepository emotionRepo;

    //EMOTION serviceImple
    @Override
    public List<EmotionEntity> findEmotions(){
        return emotionRepo.findAll();
    }
    @Override
    public Long saveEmotion(EmotionEntity emotionEntity){
        return emotionRepo.save(emotionEntity).getEmotionIdx();
    }
    @Override
    public Optional<EmotionEntity> findEmotionByName(String name){
        return emotionRepo.findEmotionByName(name);
    }
    @Override
    public void deleteEmotion(Long idx){
        emotionRepo.delete(emotionRepo.findById(idx).get());
    }

}
