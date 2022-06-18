package com.example.activity.message;

public class InviteMessage {
    private String type = "6";
    private int inviter;
    private int invited;
    private String field;

    public InviteMessage(int id1, int id2, String f)
    {
        this.inviter = id1;
        this.invited = id2;
        this.field = f;
    }

   public String MessageString()
   {
       return type+"_"+inviter+"_"+invited+"_"+field;
   }
}
