package com.example.activity.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CompeteMessage {
    private String type = "3";
    private String name;
    private int score;
    private int fini;

    public CompeteMessage(String user, int sc, int f)
    {
        name = user;
        score = sc;
        fini = f;
    }

    public String MessageString()
    {
        return type+"_"+name+"_"+score+"_"+fini+"";
    }
}
