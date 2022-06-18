package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.FriendsRequestService;
import com.example.demo.service.FriendsService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private FriendsRequestService friendsRequestService;

    @RequestMapping("/getFriends")
    public List<User> getFriends(@RequestParam("id") int id)
    {
        return friendsService.getFriends(id);

    }

    @RequestMapping("/getFriendsRequest")
    public List<User> getFriendsRequest(@RequestParam("id") int id)
    {
        return friendsRequestService.getFriendsRequest(id);

    }

    @RequestMapping("/addFriend")
    public void addFriend(@RequestParam("account_or_nickname") String account_or_nickname,@RequestParam("id") int id)
    {
        friendsRequestService.addFriendRequest(id,account_or_nickname);
    }

    @RequestMapping("/solveRequest")
    public JSONObject solveRequest(@RequestParam("user1id") int id1,@RequestParam("acceptOrRefuse") int aor,@RequestParam("user2id") int id2)
    {
        friendsRequestService.solveRequest(id1,aor,id2);
        JSONObject obj=new JSONObject();
        obj.put("solve","ok");
        return obj;
    }

    @RequestMapping("/getOnlineFriends")
    public List<User> getOnlineFriendsRequest(@RequestParam("id") int id)
    {

        //应该是在线好友

        return friendsService.getFriends(id);

    }

    @RequestMapping("/inviteBattle")
    public JSONObject inviteBattle(@RequestParam("user1id") int id1,@RequestParam("user2id")int id2)
    {
        //给id2发送battle消息，得到回复


        JSONObject obj=new JSONObject();
        obj.put("accept","yes");
        return obj;
    }
}
