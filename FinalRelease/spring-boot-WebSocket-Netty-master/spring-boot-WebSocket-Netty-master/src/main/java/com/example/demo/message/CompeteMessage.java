package com.example.demo.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CompeteMessage {
    private String type = "3";
    private String name;
    private int score;

    public CompeteMessage(String user, int sc)
    {
        name = user;
        score = sc;
    }

    public String MessageString()
    {
        return type+"_"+name+"_"+score+"";
    }
}
