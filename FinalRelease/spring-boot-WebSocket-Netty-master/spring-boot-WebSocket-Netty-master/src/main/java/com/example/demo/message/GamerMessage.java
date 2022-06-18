package com.example.demo.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GamerMessage {
    private String gamername;
    private int gamernum;
    public GamerMessage(String name, int num){
        gamername = name;
        gamernum = num;
    }
    public String getNmae()
    {
        return gamername;
    }
    public int getNum()
    {
        return gamernum;
    }
}

