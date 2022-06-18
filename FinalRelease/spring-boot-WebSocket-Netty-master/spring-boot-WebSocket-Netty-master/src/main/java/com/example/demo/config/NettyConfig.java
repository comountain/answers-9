package com.example.demo.config;


import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.List;



public class NettyConfig {
    //存储每一个客户端接入进来的对象
    public static int wrappersize = 4;
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static HashMap<Integer, String> AllonLine = new HashMap<>();
    public static HashMap<String, List<Channel>> GroupPool = new HashMap<String, List<Channel>>();
    public static HashMap<String,Channel> allplayer = new HashMap<String,Channel>();
    public static HashMap<String,Channel> MatchPool = new HashMap<String,Channel>();
    public static HashMap<String,Data> MatchHelper = new HashMap<String,Data>();
    public static HashMap<Integer,List<Channel>> room = new HashMap<>();
    public static HashMap<Integer,List<String>> roomHelper = new HashMap<>();
}
