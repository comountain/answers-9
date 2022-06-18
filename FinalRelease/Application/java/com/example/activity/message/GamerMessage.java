package com.example.activity.message;

import lombok.Data;
import lombok.ToString;
@Data
@ToString(callSuper = true)
public class GamerMessage {
    private String type = "1";
    private String gamername;
    public GamerMessage(String name){
        gamername = name;
    }
    public String MessageString()
    {
        return type+"_"+gamername;
    }
}
