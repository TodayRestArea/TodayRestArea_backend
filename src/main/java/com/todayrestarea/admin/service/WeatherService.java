package com.todayrestarea.admin.service;

import com.todayrestarea.admin.model.entity.WeatherEntity;
import java.util.Optional;

public interface WeatherService {
    Optional<WeatherEntity> findByWeatherId(Long weatherIdx);
}
