package com.todayrestarea.diary.service;

import com.todayrestarea.diary.model.DiaryListRes;

import java.util.List;

public interface DiaryService {
    List<DiaryListRes> getDiaryList(String yearMonth);
}
