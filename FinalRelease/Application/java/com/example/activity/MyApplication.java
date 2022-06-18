package com.example.activity;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import com.example.activity.bean.QuestBean;
import com.example.activity.message.AcceptMessage;
import com.example.activity.message.AnswerMessage;
import com.example.activity.message.CompeteMessage;
import com.example.activity.message.CreateRoomMessage;
import com.example.activity.message.DeleteRoomMessage;
import com.example.activity.message.InviteMessage;
import com.example.activity.message.LeaveMessage;
import com.example.activity.message.LogMessage;
import com.example.activity.message.MatchMessage;
import com.example.activity.message.RoomStartMessage;
import com.example.activity.service.CientService;
import com.example.activity.message.GamerMessage;
import com.example.activity.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {
    public User user = null;
    public String username;
    public String nickname;
    public String game_score;
    public String playtype;
    CientService cientService = new CientService();
    private List<Activity> oList;

    public String caonima(){return cientService.caonima();}

    @Override
    public void onCreate()
    {
        super.onCreate();
        oList = new ArrayList<Activity>();
    }

    public void addActivity_(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }

    public void createUser(int id, int image_id, int gamescore, String username, String nickname)
    {
        boolean havein = false;
        if(user != null)
            havein = true;
        user = new User(id,gamescore, image_id,username,nickname);
        cientService.setUser(user.getNickname());
        if(!havein)
            cientService.start();
        cientService.sendMessage(new LogMessage(id,nickname));
    }

    public void setWeak(int w)
    {
        user.setWeak(w);
    }

    public int getWeak()
    {
        return user.getWeak();
    }

    public void setBrave(int w)
    {
        user.setBrave(w);
    }

    public int getBrave()
    {
        return user.getBrave();
    }


    public void setMoney(int m){user.setMoney(m);}

    public int getMoney(){return user.getMoney();}

    public User getMyself(){return user;}

    public void finish(){this.finish();}

    public void removeUser()
    {
        cientService.senMessage(new LeaveMessage(user.getId()));
    }

    public void setNickname(String nick) {
        nickname = nick;
    }


    public void setUsername(String us) {
        username = us;
    }

    public String getAccount()
    {
        return username;
    }

    public int getImageId(){return user.getImage_id();}

    public void setImageId(int i){user.setImage_id(i);}

    public void setGame_score(String score) {
        game_score = score;
    }

    public String getGame_score() {
        return game_score;
    }

    public void removeTool(String s){user.remove_tool(s);}

    public void addTool(String s){user.add_tool(s);}

    public void setPlaytype(String ty)
    {
        playtype = ty;
    }

    public boolean ifTool(String s){return user.if_have_tool(s);}

    public int getWrapperOrder(){return cientService.wrapper_order;}

    public String getPlaytype()
    {
        return playtype;
    }

    public String getNickname()
    {
        return user.getNickname();
    }

    public String getRoomField(int id){return cientService.getRoomField(id);}

    public String[] getPlayername(){return cientService.getPlayername();}

    public HashMap<String, Integer> getInvitors(){return cientService.getInvitors();}

    public int getId(){return user.getId();}

    public int getRank(){return user.getGame_score();}

    public List<String> getWrong(){return cientService.getWrong_quest();}

    public int getSubNow(){return cientService.getSubnow();}

    public void addWrong(String q){cientService.addWrong(q);}

    public void removeInvitor(String l){cientService.removeInvite(l);}

    public void resetWrong(){cientService.resetWrong();}

    public void restSubNow(){cientService.resetSubnow();}

    public void resetClient()
    {
        cientService.resetGameresult();
        cientService.resetPlayername();
        cientService.resetIfMatched();
    }

    public void resetWrapperOrder()
    {
        cientService.wrapper_order = -1;
    }

    public void setScore(String score)
    {
        cientService.setScore(score);
    }

    public void sendMessage(GamerMessage msg)
    {
        cientService.sendMessage(msg);
    }

    public void sendMessage(MatchMessage msg)
    {
        cientService.sendMessage(msg);
    }

    public void sendMessage(CompeteMessage msg)
    {
        cientService.sendMessage(msg);
    }

    public void sendMessage(LeaveMessage msg){cientService.senMessage(msg);}

    public void sendMessage(AnswerMessage msg){cientService.sendMessage(msg);}

    public void sendMessage(InviteMessage msg){cientService.sendMessage(msg);}

    public void sendMessage(CreateRoomMessage msg){cientService.sendMessage(msg);}

    public void sendMessage(AcceptMessage msg){cientService.sendMessage(msg);}

    public void sendMessage(DeleteRoomMessage msg){cientService.sendMessage(msg);}

    public void sendMessage(RoomStartMessage msg){cientService.sendMessage(msg);}

    public boolean ifMatched()
    {
        return cientService.isIfMatched();
    }

    public HashMap<String, String> getGameresult()
    {
        return cientService.getGameresult();
    }

}
