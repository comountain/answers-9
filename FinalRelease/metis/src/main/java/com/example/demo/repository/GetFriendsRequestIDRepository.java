package com.example.demo.repository;

import com.example.demo.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface GetFriendsRequestIDRepository extends JpaRepository<FriendRequest,Integer> {
    List<FriendRequest> getFriendRequestsByUser2id(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into friendrequest values (null,?1,?2,?3)",nativeQuery = true)
    void addNewRequest(int user1id, int user2id, Date date);

    @Transactional
    @Modifying
    @Query(value = "delete from friendrequest where user1id=?1 and user2id=?2",nativeQuery = true)
    void deleteFriendRequest(int user1id,int user2id);

    List<FriendRequest> getFriendRequestsByUser1idAndUser2id(int user1id,int user2id);
}
