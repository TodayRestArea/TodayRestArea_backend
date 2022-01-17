package com.todayrestarea.diary.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import com.todayrestarea.diary.repository.DiaryRepository;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{
    final private DiaryRepository diaryRepository;


}
