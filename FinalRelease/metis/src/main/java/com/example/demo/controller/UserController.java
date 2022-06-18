package com.example.demo.controller;


import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userservice;

    @RequestMapping("/LogLet")
    public  String checkLogin(@RequestParam("username")String usn,@RequestParam("password")String psw){
        return userservice.checkLogin(usn,psw);
    }

    @RequestMapping("RegLet")
    public String signUp(@RequestParam("username")String usn,@RequestParam("password")String psw,@RequestParam("nickname")String nickname){
        return userservice.signUp(usn,psw,nickname);
    }

    @RequestMapping("/getall")
    public String getall(){
        return userservice.getall();
    }

    @RequestMapping("/getInfo")
    public String getInfo(@RequestParam("username")String username){
        return userservice.getInfo(username);
    }

    @RequestMapping("/updateInfo")
    public String updateProfile(@RequestParam("username")String username,@RequestParam("nickname")String nickname){
        return userservice.updateProfile(username,nickname);
    }

    @RequestMapping("/updateScore")
    public String updateScore(@RequestParam("username") String usn, @RequestParam("change")int change)
    {
        userservice.updateScore(usn, change);
        return "suc";
    }

    @RequestMapping("/updateMoney")
    public String updateMoney(@RequestParam("username") String usn, @RequestParam("change")int change)
    {
        userservice.updateMoney(usn, change);
        return "suc";
    }

    @RequestMapping("/updateBrave")
    public String updateBrave(@RequestParam("username") String usn, @RequestParam("change")int change)
    {
        userservice.updateBrave(usn, change);
        return "suc";
    }

    @RequestMapping("/updateWeak")
    public String updateWeak(@RequestParam("username") String usn, @RequestParam("change")int change)
    {
        userservice.updateWeak(usn, change);
        System.out.println("update weak");
        return "suc";
    }

    @RequestMapping("/updateHead")
    public String updateHead(@RequestParam("username") String usn, @RequestParam("change")int change)
    {
        userservice.updateHead(usn, change);
        System.out.println("update head");
        return "suc";
    }
}
