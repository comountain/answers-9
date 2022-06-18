package com.example.demo.service;

public interface UserService {
    String checkLogin(String usn, String psw);

    String getInfo( String usn);

    String signUp( String username, String password, String nickname);
    String getall();
    String updateProfile(String usn,String nickname);
    void updateScore(String usn, int chan);
    void updateMoney(String usn, int chan);
    void updateWeak(String usn, int chan);
    void updateBrave(String usn, int chan);
    void updateHead(String usn, int chan);
}
