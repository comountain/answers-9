package com.example.activity.message;



public class CreateRoomMessage {
    String type = "7";
    int creator_id;

    public CreateRoomMessage(int id)
    {
        creator_id = id;
    }

    public String MessageString()
    {
        return type+"_"+creator_id;
    }
}
