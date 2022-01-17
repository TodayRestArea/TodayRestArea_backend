package com.todayrestarea.admin.repository;

import com.todayrestarea.admin.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMovieRepository extends JpaRepository<Movie,Long> {
}
