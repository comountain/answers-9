package com.example.demo.message;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
public class MatchMessage {
    private String type = "2";
    private String wrapper_order;
    private String[] name;

    public MatchMessage(String wr, String[] username)
    {
        this.name = username;
        this.wrapper_order = wr;
    }

    public String MessageString()
    {
        String retmes;
        retmes = type+"_"+wrapper_order;
        for(String iter : name)
            retmes += "_" + iter;
        return retmes;
    }
}