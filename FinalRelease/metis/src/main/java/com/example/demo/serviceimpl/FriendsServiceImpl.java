package com.example.demo.serviceimpl;

import com.example.demo.dao.FriendsDao;
import com.example.demo.entity.User;
import com.example.demo.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsServiceImpl implements FriendsService {
    @Autowired
    private FriendsDao friendsDao;

    @Override
    public List<User> getFriends(int id)
    {
        return friendsDao.findById(id);
    }
}
