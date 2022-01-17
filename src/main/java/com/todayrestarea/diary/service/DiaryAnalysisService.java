package com.todayrestarea.diary.service;

import com.todayrestarea.diary.entity.DiaryEntity;
import com.todayrestarea.diary.model.DiaryAnalysis;

import java.util.List;

public interface DiaryAnalysisService {
    DiaryAnalysis analyzeDiary(Long diaryId);
}
