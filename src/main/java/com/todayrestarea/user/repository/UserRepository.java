package com.todayrestarea.user.repository;

import com.todayrestarea.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByOauthId(String oauthId);
}
