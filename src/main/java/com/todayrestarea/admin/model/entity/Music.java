package com.todayrestarea.admin.model.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@NamedQuery(name = "Music.checkExistence",
query = "select m from Music m where m.title=:title and m.artist=:artist"
)
@Table(name="music",uniqueConstraints = {
        @UniqueConstraint(//unique constraint 추가
        name = "TITLE_ARTIST_UNIQUE",
        columnNames = {"title","artist"}
        )
})
public class Music {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;
    @Column(name="emotion_id")
    private Integer emotionId;
    @Column(name="title")
    private String title;
    @Column(name="artist")
    private String artist;
    @Column(name="poster_url")
    private String posterUrl;
    @Column(name="info_url")
    private String infoUrl;
}
