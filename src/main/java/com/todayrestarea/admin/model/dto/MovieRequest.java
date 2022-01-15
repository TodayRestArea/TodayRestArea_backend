package com.todayrestarea.admin.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieRequest {
    private String movieTitle;
    private String emotionName;
}
