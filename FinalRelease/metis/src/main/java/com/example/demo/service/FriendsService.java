package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface FriendsService {
    List<User> getFriends(int id);
}
