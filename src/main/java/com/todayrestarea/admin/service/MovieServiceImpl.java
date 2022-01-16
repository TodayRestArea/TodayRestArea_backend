package com.todayrestarea.admin.service;
import com.todayrestarea.admin.model.entity.MovieEntity;
import com.todayrestarea.admin.repository.JpaMovieRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    final private JpaMovieRepository movieRepo;
    //MOVIE serviceImple
    @Override
    public List<MovieEntity> findMovies(){
        return movieRepo.findAll();
    }
    @Override
    public Long saveMovie(MovieEntity movieEntity){
        return movieRepo.save(movieEntity).getMovieIdx();
    }
    @Override
    public void deleteMovie(Long idx){
        movieRepo.delete(movieRepo.findById(idx).get());
    }
}
