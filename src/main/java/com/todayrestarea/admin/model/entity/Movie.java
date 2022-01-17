package com.todayrestarea.admin.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="movie")
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    @Column(name="emotion_id")
    private Integer emotionId;
    @Column(name="title")
    private String movieTitle;
    @Column(name="poster_url")
    private String posterUrl;
    @Column(name="info_url")
    private String infoUrl;
}
