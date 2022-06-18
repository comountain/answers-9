package com.example.activity.message;

public class AcceptMessage {
    String type = "8";
    int invitor_id;
    int self_id;

    public AcceptMessage(int id1, int id2)
    {
        invitor_id = id1;
        self_id = id2;
    }

    public String MessageString()
    {
        return type+"_"+invitor_id+"_"+self_id;
    }
}
