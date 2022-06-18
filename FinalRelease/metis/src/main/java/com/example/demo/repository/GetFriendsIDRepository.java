package com.example.demo.repository;

import com.example.demo.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface GetFriendsIDRepository extends JpaRepository<Friend,Integer> {
    List<Friend> getFriendsByUser1id(int id);
    List<Friend> getFriendsByUser2id(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into friend values (null,?,?)",nativeQuery = true)
    void insertNewFriend(int user1id,int user2id);

    List<Friend> getFriendsByUser1idAndUser2id(int user1id,int user2id);
}
