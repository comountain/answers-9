package com.example.demo.service;

import com.example.demo.config.Data;
import com.example.demo.config.NettyConfig;
import com.example.demo.service.MatchService;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.SocketAddress;

public class MatchServiceTest extends TestCase {

    MatchService service = new MatchService();

    @Before
    public void initMatch()
    {
        NettyConfig.MatchPool.clear();
        NettyConfig.MatchHelper.clear();
        NettyConfig.GroupPool.clear();
        service = new MatchService();
    }

    @Test
    public void testMatchNull()
    {
        service.startMatch(null);
        assertEquals(NettyConfig.GroupPool.size(),0);
    }

    @Test
    public void testNumIsAlp()
    {
        service.startMatch(new Data("1a","History",1));
        assertEquals(NettyConfig.GroupPool.size(),0);
    }

    @Test
    public void testFieldIsNull()
    {
        service.startMatch(new Data("3",null, 1));
        assertEquals(NettyConfig.GroupPool.size(),0);
    }

    @Test
    public void testFieldIsBlank()
    {
        service.startMatch(new Data("3","", 1));
        assertEquals(NettyConfig.GroupPool.size(),0);
    }

    @Test
    public void testNotInMatchPool()
    {
        NettyConfig.MatchHelper.put("t1",new Data("3","his",1));
        NettyConfig.MatchHelper.put("t2",new Data("3","lasa",100));
        NettyConfig.MatchHelper.put("t3",new Data("3","lasa",100));
        service.startMatch(new Data("3","lasa",100));
        assertEquals(NettyConfig.GroupPool.size(),0);
    }

    @Test
    public void testNormal()
    {
        NettyConfig.MatchHelper.put("t1",new Data("3","lasa",100));
        NettyConfig.MatchHelper.put("t2",new Data("3","lasa",100));
        NettyConfig.MatchHelper.put("t3",new Data("3","lasa",100));
        NettyConfig.MatchPool.put("t1", getChannel());
        NettyConfig.MatchPool.put("t2",getChannel());
        NettyConfig.MatchPool.put("t3",getChannel());
        service.startMatch(new Data("3","lasa",100));
        assertEquals(NettyConfig.GroupPool.size(),3);
    }

    public Channel getChannel()
    {
        return new Channel() {
            @Override
            public ChannelId id() {
                return null;
            }

            @Override
            public EventLoop eventLoop() {
                return null;
            }

            @Override
            public Channel parent() {
                return null;
            }

            @Override
            public ChannelConfig config() {
                return null;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isRegistered() {
                return false;
            }

            @Override
            public boolean isActive() {
                return false;
            }

            @Override
            public ChannelMetadata metadata() {
                return null;
            }

            @Override
            public SocketAddress localAddress() {
                return null;
            }

            @Override
            public SocketAddress remoteAddress() {
                return null;
            }

            @Override
            public ChannelFuture closeFuture() {
                return null;
            }

            @Override
            public boolean isWritable() {
                return false;
            }

            @Override
            public long bytesBeforeUnwritable() {
                return 0;
            }

            @Override
            public long bytesBeforeWritable() {
                return 0;
            }

            @Override
            public Unsafe unsafe() {
                return null;
            }

            @Override
            public ChannelPipeline pipeline() {
                return null;
            }

            @Override
            public ByteBufAllocator alloc() {
                return null;
            }

            @Override
            public Channel read() {
                return null;
            }

            @Override
            public Channel flush() {
                return null;
            }

            @Override
            public ChannelFuture bind(SocketAddress socketAddress) {
                return null;
            }

            @Override
            public ChannelFuture connect(SocketAddress socketAddress) {
                return null;
            }

            @Override
            public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1) {
                return null;
            }

            @Override
            public ChannelFuture disconnect() {
                return null;
            }

            @Override
            public ChannelFuture close() {
                return null;
            }

            @Override
            public ChannelFuture deregister() {
                return null;
            }

            @Override
            public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress1, ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture disconnect(ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture close(ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture deregister(ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture write(Object o) {
                return null;
            }

            @Override
            public ChannelFuture write(Object o, ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture writeAndFlush(Object o, ChannelPromise channelPromise) {
                return null;
            }

            @Override
            public ChannelFuture writeAndFlush(Object o) {
                return null;
            }

            @Override
            public ChannelPromise newPromise() {
                return null;
            }

            @Override
            public ChannelProgressivePromise newProgressivePromise() {
                return null;
            }

            @Override
            public ChannelFuture newSucceededFuture() {
                return null;
            }

            @Override
            public ChannelFuture newFailedFuture(Throwable throwable) {
                return null;
            }

            @Override
            public ChannelPromise voidPromise() {
                return null;
            }

            @Override
            public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
                return null;
            }

            @Override
            public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
                return false;
            }

            @Override
            public int compareTo(Channel channel) {
                return 0;
            }
        };
    }
}