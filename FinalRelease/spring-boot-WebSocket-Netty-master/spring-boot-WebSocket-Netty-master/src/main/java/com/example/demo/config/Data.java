package com.example.demo.config;

public class Data{
    String num;
    String field;
    int game_score;
    public Data(String n, String f, int s){num = n; field = f;game_score = s;}

    public String getField() {
        return field;
    }

    public String getNum() {
        return num;
    }

    public int getGame_score()
    {
        return game_score;
    }
}
