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
@NamedQuery(name="EmotionEntity.findEmotionByName",
query="select m from EmotionEntity m where m.emotionName= :emotion_name")
@Table(name="EMOTION")
public class EmotionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emotionIdx;
    @Column(name="emotion_name")
    private String emotionName;
}
