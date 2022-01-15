package com.todayrestarea.admin.service;

import com.todayrestarea.admin.common.music.MusicInfoApi;
import com.todayrestarea.admin.common.music.MusicInfoResponse;
import com.todayrestarea.admin.model.dto.MusicRequest;
import com.todayrestarea.admin.model.entity.MusicEntity;
import com.todayrestarea.admin.repository.JpaEmotionRepository;
import com.todayrestarea.admin.repository.JpaMovieRepository;
import com.todayrestarea.admin.repository.JpaMusicRepository;
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
public class MusicServiceImpl implements MusicService {
    final private JpaEmotionRepository emotionRepo;
    final private JpaMovieRepository movieRepo;
    final private JpaMusicRepository musicRepo;

    //MUSIC serviceImple
    @Override
    public List<MusicEntity> findMusics(){
        return musicRepo.findAll();
    }
    @Override
    public Long saveMusic(MusicRequest musicRequest){
        //mi : music Api
        MusicInfoApi mi=new MusicInfoApi();
        Optional<MusicInfoResponse> miResponse=mi.getMusicInfo(
                musicRequest.getMusicTitle(),
                musicRequest.getMusicArtist(),
                10
        );
        System.out.println("miResponse.toString() = " + miResponse.toString());
        if(miResponse.isEmpty()){
            return -1l;
        }else{
            MusicEntity musicEntity=new MusicEntity();
            musicEntity.setTitle(miResponse.get().getName());
            musicEntity.setArtist(miResponse.get().getArtist());
            System.out.println("musicEntity.toString()1 = " + musicEntity.toString());
            musicEntity.setPosterUrl(miResponse.get().getPosterUrl());
            musicEntity.setInfoUrl(miResponse.get().getUrl());
            musicEntity.setEmotionIdx(0);
            System.out.println("musicEntity.toString() = " + musicEntity.toString());
            return musicRepo.save(musicEntity).getMusicIdx();
        }
    }

    @Override
    public void deleteMusic(Long musicIdx) {
        Optional<MusicEntity> byId = musicRepo.findById(musicIdx);
         musicRepo.delete(byId.get());
    }

}
