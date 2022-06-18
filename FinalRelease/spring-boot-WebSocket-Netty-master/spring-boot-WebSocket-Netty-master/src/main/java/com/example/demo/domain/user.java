package com.example.demo.domain;

import io.netty.channel.Channel;

public class user {
    int id;
    String nickname;
    Channel channel;

    public user(int i, String n, Channel c)
    {
        id = i;
        nickname = n;
        channel = c;
    }

    int getId()
    {
        return id;
    }

    String getNickname()
    {
        return nickname;
    }

    Channel getChannel()
    {
        return channel;
    }
}
