package com.example.demo.service;

import com.example.demo.config.NettyConfig;
import com.example.demo.config.Data;
import com.example.demo.message.MatchMessage;
import io.netty.channel.Channel;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatchService {
    static Lock l = new ReentrantLock();
    private boolean ifStart = false;

    public boolean isIfStart() {
        return ifStart;
    }

    public MatchService()
    {
    }

    public void startMatch(Data data)
    {
        if(data == null || data.getNum() == null || data.getField() == null || data.getField().equals("") || data.getNum().equals("1"))
            return;
        List<Channel> nowgroup = new ArrayList<>();
        int groupnum = 0;
        try {
            groupnum = Integer.parseInt(data.getNum());
        }catch (NumberFormatException e)
        {
            return;
        }
        if(groupnum > 4)
            return;
        String[] playername = new String[groupnum];
        int i = 0;
        while(true)
        {
            if(NettyConfig.MatchPool.isEmpty())
            {
                System.out.println("Are you KIDDING me?!");
                ifStart = false;
                break;
            }
            if(NettyConfig.MatchPool.size() < groupnum)
            {
                System.out.println("TOO LESS, only " + NettyConfig.MatchPool.size() + "palyer");
                System.out.println("At least" + groupnum + "palyer");
                ifStart = false;
                break;
            }
            l.lock();
            for(Map.Entry<String, Data> entry: NettyConfig.MatchHelper.entrySet())
                if(entry.getValue().getNum().equals(data.getNum()) && entry.getValue().getField().equals(data.getField()))
                {
                    int def = entry.getValue().getGame_score() - data.getGame_score();
                    if(def < -50 || def > 50)
                        continue;
                    System.out.println("find" + entry.getKey());
                    if(!NettyConfig.MatchPool.containsKey(entry.getKey()))
                        return;
                    nowgroup.add(NettyConfig.MatchPool.get(entry.getKey()));
                    playername[i] = entry.getKey();
                    i++;
                    groupnum --;
                    //NettyConfig.MatchPool.remove(entry.getKey());
                }
            if(groupnum == 0)
            {
                int max = NettyConfig.wrappersize;
                Random random = new Random();
                int wrapper_order = random.nextInt(max);
                for(int j = 0; j < i; j++)
                {
                    System.out.println(playername[j] + " has been matched");
                    NettyConfig.MatchHelper.remove(playername[j]);
                    NettyConfig.MatchPool.remove(playername[j]);
                    NettyConfig.GroupPool.put(playername[j], nowgroup);
                }
                MatchMessage mes = new MatchMessage(wrapper_order+"",playername);
                for(int j = 0; j < i; j++)
                    nowgroup.get(j).writeAndFlush(mes.MessageString());
                nowgroup = new ArrayList<>();
                groupnum = Integer.parseInt(data.getNum());
                playername = new String[groupnum];
                i = 0;
                l.unlock();
                continue;
            }
            else
            {
                l.unlock();
                break;
            }
        }
    }

}
