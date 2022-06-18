package com.example.demo.controller;

import com.example.demo.config.NettyConfig;
import com.example.demo.message.CompeteMessage;
import com.example.demo.message.InviteMessage;
import com.example.demo.message.MatchMessage;
import com.example.demo.message.RoomMessage;
import com.example.demo.service.MatchService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.config.Data;
import java.util.*;

@Controller
@RestController
public class StartWebSocket {
    @GetMapping("/action/webSocket")
    public void main(String[] args){
        System.out.println("here start work");
        MatchService service = new MatchService();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss,worker);
            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                protected void initChannel(NioSocketChannel ch) throws Exception{
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive (ChannelHandlerContext context)throws Exception{
                            NettyConfig.group.add(context.channel());
                            System.out.println("客户端与服务端连接开启");
                        }

                        @Override
                        public void channelInactive(ChannelHandlerContext context)throws Exception{
                            String user_name = null;
                            int user_id = 0;
                            NettyConfig.group.remove(context.channel());
                            for(Map.Entry<String, Channel> x: NettyConfig.allplayer.entrySet())
                            {
                                if(x.getValue() == context.channel())
                                {
                                    System.out.println(x.getKey() + " leave us");
                                    user_name = x.getKey();
                                    break;
                                }
                            }
                            for(Map.Entry<Integer, String> x: NettyConfig.AllonLine.entrySet())
                            {
                                if(x.getValue().equals(user_name))
                                {
                                    System.out.println(user_name + "'s id is " + x.getKey());
                                    user_id = x.getKey();
                                }
                            }
                            if(NettyConfig.room.get(user_id) != null)
                            {
                                System.out.println(user_name + " has already created a room ! Let's tell others in the room");
                                List<String> all_g1 = NettyConfig.roomHelper.get(user_id);
                                all_g1.remove(user_name);
                                List<Channel> room_c1 = NettyConfig.room.get(user_id);
                                room_c1.remove(context.channel());
                                for(Channel ch : room_c1)
                                {
                                    System.out.println(ch + "receive leave message");
                                    ch.writeAndFlush(new RoomMessage(0, all_g1.toArray(new String[all_g1.size()])).MessageString());
                                }
                                NettyConfig.room.remove(user_id);
                                System.out.println("We have remove the room created by " + user_name);
                            }
                            if(NettyConfig.MatchPool.containsKey(user_name))
                            {
                                System.out.println(user_name + " is in pool");
                                NettyConfig.MatchPool.remove(user_name);
                            }
                            if(NettyConfig.MatchHelper.containsKey(user_name))
                            {
                                System.out.println(user_name + " is in helper");
                                NettyConfig.MatchHelper.remove(user_name);
                            }
                            if(NettyConfig.GroupPool.containsKey(user_name))
                            {
                                System.out.println(user_name + " is in a game");
                                NettyConfig.GroupPool.remove(user_name);
                            }
                            NettyConfig.AllonLine.remove(user_id);
                            System.out.println("AllonLine's size is: " + NettyConfig.AllonLine.size());
                            NettyConfig.allplayer.remove(user_name);
                            System.out.println("allplayer's size is: " + NettyConfig.allplayer.size());
                        }

                        public void channelRead(ChannelHandlerContext ctx, Object msg){
                            String command = (String)msg;
                            String []mes = command.split("_");
                            switch(mes[0])
                            {
                                //no use anymore
                                case "1":
                                    NettyConfig.allplayer.put(mes[1],ctx.channel());
                                    System.out.println(mes[1] + " join");
                                    System.out.println(NettyConfig.allplayer.size()+" players now");
                                    break;
                                case "2":
                                    if(mes[4].equals("1"))
                                    {
                                        NettyConfig.MatchPool.remove(mes[1]);
                                        NettyConfig.MatchHelper.remove(mes[1]);
                                        System.out.println(mes[1] + "has been removed");
                                        System.out.println("Pool.size: " + NettyConfig.MatchPool.size());
                                        System.out.println("Match.size: " + NettyConfig.MatchHelper.size());
                                        break;
                                    }
                                    NettyConfig.MatchPool.put(mes[1],ctx.channel());
                                    NettyConfig.MatchHelper.put(mes[1], new Data(mes[2], mes[3], Integer.parseInt(mes[5])));
                                    if(service.isIfStart())
                                        break;
                                    else
                                    {
                                        Thread thread = new Thread()
                                        {
                                            @Override
                                            public void run() {
                                                service.startMatch(new Data(mes[2], mes[3], Integer.parseInt(mes[5])));
                                            }
                                        };
                                        thread.start();
                                    }
                                    break;
                                case "3":
                                    CompeteMessage mess = new CompeteMessage(mes[1],Integer.parseInt(mes[2]));
                                    List<Channel> intr = NettyConfig.GroupPool.get(mes[1]);
                                    if(mes[3].equals("1"))
                                    {
                                        NettyConfig.GroupPool.remove(mes[1]);
                                        break;
                                    }
                                    System.out.println(mes[1]+" has make answer and his/her score is:" + mes[2]);
                                    for(Channel in : intr)
                                    {
                                        in.writeAndFlush(mess.MessageString());
                                        System.out.println();
                                        System.out.println(mess.MessageString());
                                    }
                                    break;
                                case "4":
                                    int id_leave = Integer.parseInt(mes[1]);
                                    String nick = NettyConfig.AllonLine.get(id_leave);
                                    System.out.println(nick + "leave");
                                    NettyConfig.allplayer.remove(nick);
                                    NettyConfig.AllonLine.remove(id_leave);
                                    if(NettyConfig.MatchPool.containsKey(nick))
                                    {

                                        NettyConfig.MatchPool.remove(nick);
                                        NettyConfig.MatchHelper.remove(nick);
                                    }

                                    System.out.println(NettyConfig.AllonLine.size() + "player now");
                                    break;
                                case "5":
                                    int id = Integer.parseInt(mes[1]);
                                    String nickname = mes[2];
                                    System.out.println(nickname + " join us");
                                    NettyConfig.allplayer.put(nickname,ctx.channel());
                                    NettyConfig.AllonLine.put(id, nickname);
                                    break;
                                case "6":
                                    int id_inv = Integer.parseInt(mes[1]);
                                    int id_inved = Integer.parseInt(mes[2]);
                                    String fie = mes[3];
                                    String nickname1 = NettyConfig.AllonLine.get(id_inv);
                                    String nickname2 = NettyConfig.AllonLine.get(id_inved);
                                    System.out.println(nickname1 + " invite " + nickname2);
                                    NettyConfig.allplayer.get(nickname2).writeAndFlush(new InviteMessage(id_inv, nickname1, fie).MessageString()
                                    );

                                    break;
                                case "7":
                                    int creator_id = Integer.parseInt(mes[1]);
                                    System.out.println(creator_id+" create a room");
                                    List<Channel> chan = new ArrayList<>();
                                    List<String> all = new ArrayList<>();
                                    chan.add(ctx.channel());
                                    String name = NettyConfig.AllonLine.get(creator_id);
                                    all.add(name);
                                    NettyConfig.room.put(creator_id,chan);
                                    NettyConfig.roomHelper.put(creator_id,all);
                                    break;
                                case "8":
                                    int invi_id = Integer.parseInt(mes[1]);
                                    int sel_id = Integer.parseInt(mes[2]);
                                    System.out.println(sel_id + " accept invitation from " + invi_id);
                                    System.out.println(ctx.channel() + "accept");
                                    List<String> all_g = NettyConfig.roomHelper.get(invi_id);
                                    List<Channel> room_c = NettyConfig.room.get(invi_id);
                                    String sel_name = NettyConfig.AllonLine.get(sel_id);
                                    if(all_g.contains(sel_name))
                                    {
                                        System.out.println(sel_name + " already in room");
                                        RoomMessage roomMessage = new RoomMessage(invi_id, all_g.toArray(new String[all_g.size()]));
                                        System.out.println(roomMessage.MessageString());
                                        for(Channel ch : room_c)
                                            ch.writeAndFlush(roomMessage.MessageString());
                                        System.out.println(roomMessage.MessageString());
                                        break;
                                    }
                                    all_g.add(sel_name);
                                    room_c.add(ctx.channel());
                                    NettyConfig.room.put(invi_id, room_c);
                                    NettyConfig.roomHelper.put(invi_id, all_g);
                                    RoomMessage roomMessage = new RoomMessage(invi_id, all_g.toArray(new String[all_g.size()]));
                                    System.out.println(roomMessage.MessageString());
                                    int m = 0;
                                    for(Channel ch : room_c)
                                    {
                                        m ++;
                                        System.out.println(ch + "rece");
                                        ch.writeAndFlush(roomMessage.MessageString());
                                    }
                                    System.out.println(m + " players now in room of" + NettyConfig.AllonLine.get(invi_id));
                                    break;
                                case "9":
                                    int de_id = Integer.parseInt(mes[1]);
                                    String de_name = mes[3];
                                    if(mes[2].equals("1"))
                                    {
                                        List<String> all_g1 = NettyConfig.roomHelper.get(de_id);
                                        all_g1.remove(de_name);
                                        List<Channel> room_c1 = NettyConfig.room.get(de_id);
                                        for(Channel ch : room_c1)
                                        {
                                            System.out.println(ch + "receive leave message");
                                            ch.writeAndFlush(new RoomMessage(0, all_g1.toArray(new String[all_g1.size()])).MessageString());
                                        }
                                        NettyConfig.room.remove(de_id);
                                        System.out.println(de_id + "" + NettyConfig.AllonLine.get(de_id) + " remove room");
                                        break;
                                    }
                                    else
                                    {
                                        List<String> all_g1 = NettyConfig.roomHelper.get(de_id);
                                        List<Channel> room_c1 = NettyConfig.room.get(de_id);
                                        all_g1.remove(de_name);
                                        room_c1.remove(NettyConfig.allplayer.get(de_name));
                                        NettyConfig.room.put(de_id, room_c1);
                                        NettyConfig.roomHelper.put(de_id, all_g1);
                                        RoomMessage roomMessage1 = new RoomMessage(de_id, all_g1.toArray(new String[all_g1.size()]));
                                        for(Channel ch : room_c1)
                                        {
                                            System.out.println(ch + "receive leave message");
                                            ch.writeAndFlush(roomMessage1.MessageString());
                                        }
                                        System.out.println(de_name + " has leaved " + NettyConfig.AllonLine.get(de_id) + "'s room and remain" + all_g1.size() + "player(s) now");
                                    }
                                    break;
                                case "a":
                                    int sta_id = Integer.parseInt(mes[1]);
                                    List<String> all_g2 = NettyConfig.roomHelper.get(sta_id);
                                    List<Channel> room_c2 = NettyConfig.room.get(sta_id);
                                    NettyConfig.roomHelper.remove(sta_id);
                                    NettyConfig.room.remove(sta_id);
                                    for(int i = 0; i < all_g2.size(); i++)
                                    {
                                        System.out.println(all_g2.get(i) + " has received the room start message");
                                        NettyConfig.GroupPool.put(all_g2.get(i), room_c2);
                                    }
                                    int max = NettyConfig.wrappersize;
                                    Random random = new Random();
                                    int wrapper_order = random.nextInt(max);
                                    MatchMessage mes_room = new MatchMessage(wrapper_order+"", all_g2.toArray(new String[all_g2.size()]));
                                    for(Channel ch : room_c2)
                                    {
                                        ch.writeAndFlush(mes_room.MessageString());
                                    }
                                    break;
                                default:
                                    break;

                            }
                        }
                    });
                }
            });
            Channel channel = serverBootstrap.bind(8888).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e){

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
};