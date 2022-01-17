package com.todayrestarea.diary.service;

import com.todayrestarea.diary.entity.Diary;
import com.todayrestarea.diary.model.DiaryListRes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import com.todayrestarea.diary.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{
    final private DiaryRepository diaryRepository;


    @Override
    public List<DiaryListRes> getDiaryList(Long userId, String yearMonth) {

        List<Diary> diaryList = diaryRepository.findDiariesByCreatedDate(userId, yearMonth);
        List<DiaryListRes> diaryResList = new ArrayList<DiaryListRes>();

        for (Diary diary : diaryList) {
            diaryResList.add(DiaryListRes.of(diary.getDiaryId(), diary.getCreatedDate(), diary.getEmotion().getEmotionId()));
        }

        return diaryResList;
    }
}
