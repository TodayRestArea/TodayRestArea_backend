package com.todayrestarea.diary.entity;


import com.todayrestarea.admin.model.entity.EmotionEntity;
import com.todayrestarea.admin.model.entity.WeatherEntity;
import com.todayrestarea.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="diary")
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    @Lob
    @Column(name="contents")
    private String contents;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User writer;

    @ManyToOne
    @JoinColumn(name="weather_id")
    private WeatherEntity weather;

    @ManyToOne
    @JoinColumn(name="emotion_id")
    private EmotionEntity emotion;

    @Temporal(value = TemporalType.DATE)
    @UpdateTimestamp
    private Date updatedAt;

    @Temporal(value = TemporalType.DATE)
    @CreationTimestamp
    private Date createdAt;
}
