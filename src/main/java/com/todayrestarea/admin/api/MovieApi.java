package com.todayrestarea.admin.api;

import com.todayrestarea.admin.model.dto.AdminResponse;
import com.todayrestarea.admin.model.dto.MovieRequest;
import com.todayrestarea.admin.model.entity.MovieEntity;
import com.todayrestarea.admin.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/movies")
public class MovieApi {
    final private MovieService movieService;
    @GetMapping("")
    @ResponseBody
    public AdminResponse movieList(){
        AdminResponse res = new AdminResponse();
        try{
            List<MovieEntity> movies = movieService.findMovies();
            if(movies.size()==0){
                res.setMessage("success but data empty");
            }
            res.setResult(movies);
        }catch(Exception e){
            res.setCode(404);
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }finally {
            return res;
        }
    }

    @PostMapping("")
    @ResponseBody
    public AdminResponse movieAdd(@RequestBody MovieRequest movieRequest) {
        AdminResponse res = new AdminResponse();
        try {
           res.setMessage("yet developed");
           MovieEntity movieEntity=new MovieEntity();
           movieEntity.setEmotionIdx(0);
           movieEntity.setMovieTitle("temp movie title");
           movieEntity.setInfoUrl("temp info url");
           movieEntity.setPosterUrl("temp poster url");
            Long idx=movieService.saveMovie(movieEntity);
            Map<String,Long> result=new HashMap<>();
            result.put("movieIdx",idx);
            res.setResult(result);
        } catch (Exception e) {
            res.setCode(404);
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        } finally {
            return res;
        }
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public AdminResponse movieDelete(@PathVariable(value = "id")Long movieIdx) {
        AdminResponse res = new AdminResponse();
        try {
            movieService.deleteMovie(movieIdx);
            //res.setResult();
        } catch (Exception e) {
            res.setCode(404);
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        } finally {
            return res;
        }
    }
}
