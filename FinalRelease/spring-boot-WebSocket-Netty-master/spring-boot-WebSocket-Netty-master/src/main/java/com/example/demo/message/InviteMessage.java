package com.example.demo.message;

public class InviteMessage {
    String type = "4";
    int invitorId;
    String invitor;
    String field;

    public InviteMessage(int id, String name, String f)
    {
        invitorId = id;
        invitor = name;
        field = f;
    }

    public String MessageString()
    {
        return type+"_"+invitorId+"_"+invitor+"_"+field;
    }
}
