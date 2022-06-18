package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface FriendsRequestService {
    List<User> getFriendsRequest(int id);
    void addFriendRequest(int id,String actOrNik);

    void solveRequest(int id1,int aor,int id2);
}
