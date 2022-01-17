package com.todayrestarea.diary.service;

import com.todayrestarea.common.dto.BaseException;
import com.todayrestarea.diary.entity.Diary;
import com.todayrestarea.diary.model.PostDiaryRequest;
import com.todayrestarea.diary.model.PutDiaryRequest;

import java.util.Date;

public interface DiaryService {
    Diary createDiary(Long userId, Date date, PostDiaryRequest postDiaryRequest) throws BaseException;
    Diary updateDiary(Long diaryId, Long userId, PutDiaryRequest putDiaryRequest) throws BaseException;
}
