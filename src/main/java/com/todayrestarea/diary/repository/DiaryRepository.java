package com.todayrestarea.diary.repository;

import com.todayrestarea.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {

}
