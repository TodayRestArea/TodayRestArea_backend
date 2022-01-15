package com.todayrestarea.admin.api;

import com.todayrestarea.admin.model.dto.AdminResponse;
import com.todayrestarea.admin.service.EmotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/emotions")
public class EmotionApi {
    final private EmotionService emotionService;
    @GetMapping("")
    @ResponseBody
    public AdminResponse emotionList(){
        System.out.println("aa");
        return new AdminResponse();
    }

    @PostMapping("")
    @ResponseBody
    public AdminResponse emotionAdd() {
        return new AdminResponse();
    }
}