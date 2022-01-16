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
import java.util.Optional;

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
     * @param musicRequest
     * @return
     */
    @PostMapping("")
    @ResponseBody
    public AdminResponse musicAdd(@RequestBody MusicRequest musicRequest) {
        AdminResponse res = new AdminResponse();
        try {
            Optional<MusicEntity> existEntity=musicService.isExist(
                    musicRequest.getMusicTitle(),
                    musicRequest.getMusicArtist()
            );
            if(existEntity.isPresent()){
                res.setResult(existEntity.get());
               throw new Exception("이미 존재하는 제목-아티스트 입니다") ;
            }else{
                Long resultIdx = musicService.saveMusic(musicRequest);
                res.setResult(resultIdx);
            }

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
