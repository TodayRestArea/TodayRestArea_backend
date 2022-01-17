package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.entity.Movie;

import java.util.List;

public interface MovieService {
    //MOVIE service
    List<Movie> findMovies();
    Long saveMovie(Movie movie);
    void deleteMovie(Long idx);
}
