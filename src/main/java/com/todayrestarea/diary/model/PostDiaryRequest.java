package com.todayrestarea.diary.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostDiaryRequest {
    private int weatherIdx;
    private String contents;
    private String cratedAt; //2021-01-14 형식
}
