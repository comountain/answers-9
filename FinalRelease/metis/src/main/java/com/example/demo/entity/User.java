package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    String username;
    String password;
    String nickname;
    int game_score;
    int image_id;
    int money;
    int weak;
    int brave;

    public User(){}

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGame_score() {
        return game_score;
    }

    public String getPassword() {
        return password;
    }

    public int getImage_id() {
        return image_id;
    }

    public Integer getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setGame_score(int game_score) {
        this.game_score = game_score;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
