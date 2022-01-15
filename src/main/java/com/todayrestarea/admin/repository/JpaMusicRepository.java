package com.todayrestarea.admin.repository;

import com.todayrestarea.admin.model.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMusicRepository extends JpaRepository<MusicEntity,Long> {
}
