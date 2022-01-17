package com.todayrestarea.admin.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity @Table(name="weather")
public class WeatherEntity {

    @Id @Column(name="weather_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherIdx;

    @Column(name="weather")
    private String weather;

}
