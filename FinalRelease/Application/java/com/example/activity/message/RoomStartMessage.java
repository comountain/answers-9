package com.example.activity.message;

public class RoomStartMessage {
    private String type = "a";
    private int id;

    public RoomStartMessage(int i)
    {
        id = i;
    }

    public String MessageString()
    {
        return type + "_" + id;
    }
}
