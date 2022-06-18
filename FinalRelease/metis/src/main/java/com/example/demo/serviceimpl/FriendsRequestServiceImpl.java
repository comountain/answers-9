package com.example.demo.serviceimpl;

import com.example.demo.dao.FriendsRequestDao;
import com.example.demo.entity.User;
import com.example.demo.service.FriendsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsRequestServiceImpl implements FriendsRequestService {
    @Autowired
    private FriendsRequestDao friendsRequestDao;

    @Override
    public List<User> getFriendsRequest(int id)
    {
        return friendsRequestDao.findById(id);
    }

    @Override
    public void addFriendRequest(int id,String actOrNik){
        friendsRequestDao.addFriendRequest(id,actOrNik);
    }
    @Override
    public void solveRequest(int id1,int aor,int id2){
        friendsRequestDao.solveRequest(id1,aor,id2);
    }
}
