package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<User,Integer> {
    User findById(int id);

    User findByUsername(String account);

    User findByNickname(String nickname);
}
