package com.todayrestarea.admin.repository;

import com.todayrestarea.admin.model.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMovieRepository extends JpaRepository<MovieEntity,Long> {
}
