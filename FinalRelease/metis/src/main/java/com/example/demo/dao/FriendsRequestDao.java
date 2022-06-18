package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface FriendsRequestDao {
    List<User> findById(int id);

    void addFriendRequest(int id,String actOrNik);

    void solveRequest(int id1,int aor,int id2);
}
