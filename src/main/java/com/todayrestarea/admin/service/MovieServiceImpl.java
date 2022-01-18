package com.todayrestarea.admin.service;
import com.todayrestarea.admin.common.movie.MovieInfoApi;
import com.todayrestarea.admin.common.movie.MovieInfoResponse;
import com.todayrestarea.admin.common.music.MusicInfoApi;
import com.todayrestarea.admin.common.music.MusicInfoResponse;
import com.todayrestarea.admin.model.dto.MovieRequest;
import com.todayrestarea.admin.model.entity.Emotion;
import com.todayrestarea.admin.model.entity.Movie;
import com.todayrestarea.admin.model.entity.Music;
import com.todayrestarea.admin.repository.JpaEmotionRepository;
import com.todayrestarea.admin.repository.JpaMovieRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    final private JpaMovieRepository movieRepo;
    final private JpaEmotionRepository emotionRepo;
    //MOVIE serviceImple
    @Override
    public List<Movie> findMovies(){
        return movieRepo.findAll();
    }
    @Override
    public Optional<Movie> findById(Long id){
        return movieRepo.findById(id);
    }
    @Override
    public Optional<Movie> isExist(String title,String director){
        return movieRepo.checkExistence(title, director);
    }
    @Override
    public Long saveMovie(MovieRequest movieRequest){
        MovieInfoApi mi=new MovieInfoApi();
        Optional<MovieInfoResponse> miResponse=mi.getMovieInfo(
                movieRequest.getMovieTitle(),
                movieRequest.getMovieDirector()
        );
        System.out.println("miResponse.toString() = " + miResponse.toString());
        if(miResponse.isEmpty()){
            return -1l;
        }else{
            Movie movie=new Movie();
            Optional<Emotion> emotion=emotionRepo.findById(movieRequest.getEmotionId());
            if(emotion.isPresent()){
                movie.setTitle(miResponse.get().getTitle());
                movie.setDirector(miResponse.get().getDirector());
                movie.setPlot(miResponse.get().getPlot());
                movie.setPosterUrl(miResponse.get().getPosterUrl());
                movie.setInfoUrl(miResponse.get().getInfoUrl());
                movie.setEmotion(emotion.get());

            }else{
                return null;
            }
            //받은 정보 이용한 entity 초기화
            Movie resIdx=movieRepo.save(movie);
            System.out.println("musicEntity.toString() = " + movie.toString());
            return movie.getMovieId();
        }
    }
    @Override
    public void deleteMovie(Long idx){
        movieRepo.delete(movieRepo.findById(idx).get());
    }
}
