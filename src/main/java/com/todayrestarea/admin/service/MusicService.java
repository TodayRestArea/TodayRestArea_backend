package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.dto.MusicRequest;
import com.todayrestarea.admin.model.entity.MusicEntity;

import java.util.List;

public interface MusicService {
    //MUSIC service
    List<MusicEntity> findMusics();
    Long saveMusic(MusicRequest musicRequest);
    void deleteMusic(Long musicIdx);
}
