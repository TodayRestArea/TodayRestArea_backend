package com.todayrestarea.admin.api;

import com.todayrestarea.admin.model.dto.AdminResponse;
import com.todayrestarea.admin.service.EmotionService;
import com.todayrestarea.admin.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/movies")
public class MovieApi {
    final private MovieService movieService;
    @GetMapping("")
    @ResponseBody
    public AdminResponse movieList(){
        System.out.println("aa");
        return new AdminResponse();
    }

    @PostMapping("")
    @ResponseBody
    public AdminResponse movieAdd() {
        return new AdminResponse();
    }
}
