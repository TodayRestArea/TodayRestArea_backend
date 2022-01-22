package com.todayrestarea.diary.service;

import com.todayrestarea.diary.entity.Diary;
import com.todayrestarea.diary.model.DiaryAnalysis;

import java.util.List;

public interface DiaryAnalysisService {
    DiaryAnalysis getFullAnalysisResult(Long diaryId) throws Exception;
    DiaryAnalysis analyzeDiary(Long diaryId) throws Exception;
}
