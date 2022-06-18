package com.example.activity.message;

public class DeleteRoomMessage {
    String type = "9";
    int id;
    int whet_owner;
    String name;

    public DeleteRoomMessage(int i, int w, String name)
    {
        id = i;
        whet_owner = w;
        this.name = name;
    }

    public String MessageString()
    {
        return type + "_" + id + "_" + whet_owner+"_"+name;
    }
}
