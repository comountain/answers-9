package com.example.demo.daoimpl;

import com.example.demo.dao.FriendsRequestDao;
import com.example.demo.entity.FriendRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.FriendsRepository;
import com.example.demo.repository.GetFriendsIDRepository;
import com.example.demo.repository.GetFriendsRequestIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class FriendsRequestDaoImpl implements FriendsRequestDao {
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private GetFriendsRequestIDRepository getFriendsRequestIDRepository;
    @Autowired
    private GetFriendsIDRepository getFriendsIDRepository;

    @Override
    public List<User> findById(int id)
    {
        List<FriendRequest> friendsRequesstId;
        friendsRequesstId=getFriendsRequestIDRepository.getFriendRequestsByUser2id(id);
//        System.out.println("friendsRequest to id"+id+" is ");
        List<User> friends = new ArrayList<>();
        for(int i=0;i<friendsRequesstId.size();++i){
            friends.add(friendsRepository.findById(friendsRequesstId.get(i).getUser1id()));
//            System.out.println(friendsRequesstId.get(i).getUser1id());
        }

        return friends;
    }

    @Override
    public void addFriendRequest(int id,String actOrNik){
        User user=new User();
        Date date=new Date();
        if(friendsRepository.findByUsername(actOrNik)!=null){
            user=friendsRepository.findByUsername(actOrNik);
            if(user.getId()==id) return;//user want to add itself
            if((getFriendsRequestIDRepository.getFriendRequestsByUser1idAndUser2id(id,user.getId())).size()!=0)//user has sent request
                return;
            if((getFriendsRequestIDRepository.getFriendRequestsByUser1idAndUser2id(user.getId(),id)).size()!=0)//target has sent request to user
                return;
            if((getFriendsIDRepository.getFriendsByUser1idAndUser2id(id, user.getId())).size()!=0)//they are friends
                return;
            getFriendsRequestIDRepository.addNewRequest(id,user.getId(),date);
        }
        else if(friendsRepository.findByNickname(actOrNik)!=null){
            user=friendsRepository.findByNickname(actOrNik);
            if (user.getId()==id) return;//user want to add itself
            if((getFriendsRequestIDRepository.getFriendRequestsByUser1idAndUser2id(id,user.getId())).size()!=0)//user has sent request
                return;
            if((getFriendsRequestIDRepository.getFriendRequestsByUser1idAndUser2id(user.getId(),id)).size()!=0)//target has sent request to user
                return;
            if((getFriendsIDRepository.getFriendsByUser1idAndUser2id(id, user.getId())).size()!=0)//they are friends
                return;
            getFriendsRequestIDRepository.addNewRequest(id,user.getId(),date);
        }
    }

    @Override
    public void solveRequest(int id1,int aor,int id2){
        getFriendsRequestIDRepository.deleteFriendRequest(id2,id1);

        if(aor==1){//accept
            getFriendsIDRepository.insertNewFriend(id1,id2);
            //getFriendsIDRepository.insertNewFriend(id2,id1);
        }
    }
}
