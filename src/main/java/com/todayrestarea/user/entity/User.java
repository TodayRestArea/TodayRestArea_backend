package com.todayrestarea.user.entity;

import com.todayrestarea.auth.kakao.dto.KakaoUserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String oauthId;

    private String userName;

    private String ageRange;

    private String gender;

    @CreatedDate
    private LocalDateTime createdAt;

    private String refreshToken;

    @Builder
    User(String oauthId, String userName, String ageRange, String gender) {
        this.oauthId = oauthId;
        this.userName = userName;
        this.ageRange = ageRange;
        this.gender = gender;
    }

    public static User newKaKaoInstance(KakaoUserResponse userInfo) {
        return User.builder()
                .oauthId(userInfo.getId())
                .userName(userInfo.getNickName())
                .ageRange(userInfo.getAge_range())
                .gender(userInfo.getGender())
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void removeRefreshToken() {
        this.refreshToken = null;
    }

}
