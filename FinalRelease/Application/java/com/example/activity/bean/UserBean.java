package com.example.activity.bean;


public class UserBean {
    private int userid;
    private String Username;
    private int image_id=1;
    private int game_score;
    private String password;
    private String nickname;

    public UserBean(int id,int game_score,String username,String nickname){
        this.userid=id;
        this.game_score=game_score;
        this.Username=username;
        this.nickname=nickname;
    }

    public UserBean(int id,String account,String password,String nickname,String img,int rank){
        this.userid=id;
        this.Username=account;
        this.password=password;
        this.nickname=nickname;
        this.image_id = Integer.parseInt(img);
        this.game_score=rank;
    }


    public int getGame_score() {
        return game_score;
    }

    public int getUserid() {
        return userid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return Username;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setGame_score(int game_score) {
        this.game_score = game_score;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        Username = username;
    }
}

