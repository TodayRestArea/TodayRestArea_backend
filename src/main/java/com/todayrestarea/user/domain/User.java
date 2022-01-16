package com.todayrestarea.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column
    private String oauthId;

    @Column
    private String userName;

    @Column
    private String ageRange;

    @Column
    private String gender;

    @Column
    private Timestamp createdAt;

    @Transient
    private String refreshToken;

    @Builder
    User(String oauthId, String userName, String ageRange, String gender) {
        this.oauthId = oauthId;
        this.userName = userName;
        this.ageRange = ageRange;
        this.gender = gender;
    }

    public static User newKaKaoInstance(String oauthId, String userName, String ageRange, String gender) {
        return User.builder()
                .oauthId(oauthId)
                .userName(userName)
                .ageRange(ageRange)
                .gender(gender)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
