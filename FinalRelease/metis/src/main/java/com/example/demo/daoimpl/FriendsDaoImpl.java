package com.example.demo.daoimpl;

import com.example.demo.dao.FriendsDao;
import com.example.demo.entity.Friend;
import com.example.demo.entity.User;
import com.example.demo.repository.FriendsRepository;
import com.example.demo.repository.GetFriendsIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendsDaoImpl implements FriendsDao {
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private GetFriendsIDRepository getFriendsIDRepository;

    @Override
    public List<User> findById(int id)
    {
        List<Friend> friendsId1, friendsId2;
        friendsId1=getFriendsIDRepository.getFriendsByUser1id(id);

        System.out.println("friends of id"+id+" is ");
        List<User> friends = new ArrayList<>();
        for(int i=0;i<friendsId1.size();++i){
            friends.add(friendsRepository.findById(friendsId1.get(i).getUser2id()));
            System.out.println(friendsId1.get(i).getUser2id());
        }

        friendsId2=getFriendsIDRepository.getFriendsByUser2id(id);
        for(int i=0;i<friendsId2.size();++i){
            friends.add(friendsRepository.findById(friendsId2.get(i).getUser1id()));
            System.out.println(friendsId2.get(i).getUser1id());
        }

        return friends;
    }

}
