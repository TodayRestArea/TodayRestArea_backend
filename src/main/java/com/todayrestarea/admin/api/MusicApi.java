package com.todayrestarea.admin.api;

import com.todayrestarea.admin.model.dto.AdminResponse;
import com.todayrestarea.admin.model.dto.MusicRequest;
import com.todayrestarea.admin.model.entity.MusicEntity;
import com.todayrestarea.admin.service.EmotionService;
import com.todayrestarea.admin.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/musics")
public class MusicApi {
    final private MusicService musicService;
    @GetMapping("")
    @ResponseBody
    public AdminResponse musicList(){
        AdminResponse res = new AdminResponse();
        try{
            List<MusicEntity> musics = musicService.findMusics();
            if(musics.size()==0){
                res.setMessage("success but data empty");
            }
            res.setResult(musics);
        }catch(Exception e){
            res.setCode(404);
            res.setSuccess(false);
            res.setMessage(e.getMessage());
        }finally {
            return res;
        }
    }

    /**
     * TODO 노래-가수 중복 체크, 감정 설정 , 이미지가 없을때
     * @param musicRequest
     * @return
     */
    @PostMapping("")
    @ResponseBody
    public AdminResponse musicAdd(@RequestBody MusicRequest musicRequest) {
        AdminResponse res = new AdminResponse();
        try {
            Long resultIdx = musicService.saveMusic(musicRequest);
            res.setResult(resultIdx);
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
    public AdminResponse musicDelete(@PathVariable(value = "id")Long musicIdx) {
        AdminResponse res = new AdminResponse();
        try {
            musicService.deleteMusic(musicIdx);
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
