package com.todayrestarea.admin.repository;

import com.todayrestarea.admin.model.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository  extends JpaRepository<WeatherEntity,Long> {
}
