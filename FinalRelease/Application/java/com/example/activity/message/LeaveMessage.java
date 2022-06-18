package com.example.activity.message;

public class LeaveMessage {
    private String type = "4";
    private int id;

    public LeaveMessage(int id)
    {
        this.id = id;
    }

    public String MessageString()
    {
        return type+"_"+id;
    }
}
