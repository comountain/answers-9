package com.example.demo.message;

public class RoomMessage {
    String type = "5";
    int owner_id;
    String[] players;

    public RoomMessage(int id, String[] p)
    {
        int size = p.length;
        owner_id = id;
        players = new String[size];
        for(int i = 0; i < size; i++)
            players[i] = p[i];
    }

    public String MessageString()
    {
        String ret = type + "_" + owner_id;
        int size = players.length;
        for(int i = 0; i < size; i++)
            ret = ret + "_" + players[i];
        return ret;
    }
}
