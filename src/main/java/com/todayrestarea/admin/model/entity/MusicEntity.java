package com.todayrestarea.admin.model.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="MUSIC")
public class MusicEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicIdx;
    @Column(name="emotion_seq")
    private Integer emotionIdx;
    @Column(name="title")
    private String title;
    @Column(name="artist")
    private String artist;
    @Column(name="poster_url")
    private String posterUrl;
    @Column(name="info_url")
    private String infoUrl;
}
