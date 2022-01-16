package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.dto.MusicRequest;
import com.todayrestarea.admin.model.entity.EmotionEntity;
import com.todayrestarea.admin.model.entity.MovieEntity;
import com.todayrestarea.admin.model.entity.MusicEntity;

import java.util.List;

public interface MovieService {
    //MOVIE service
    List<MovieEntity> findMovies();
    Long saveMovie(MovieEntity movieEntity);
    void deleteMovie(Long idx);
}
