package com.example.activity.message;

import lombok.Data;

@Data
public class AnswerMessage {
    private String type = "101010";
    private String name;
    private String score;

    public AnswerMessage(String sc, String na)
    {
        name = na;
        score = sc;
    }

    public String MessageString()
    {
        return type+"_"+name+"_"+score+"";
    }
}
