package com.example.activity.message;

public class LogMessage {
    private String type = "5";
    private int id;
    private String nickname;

    public LogMessage(int id, String nick)
    {
        this.id = id;
        this.nickname = nick;
    }

    public String MessageString()
    {
        return type+"_"+id+"_"+nickname;
    }
}
