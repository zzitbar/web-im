package com.zzitbar.webim.netty;

import com.alibaba.fastjson.JSON;
import com.zzitbar.webim.Constants;
import com.zzitbar.webim.cache.CacheUtil;
import com.zzitbar.webim.dto.GroupDto;
import com.zzitbar.webim.dto.Message;
import com.zzitbar.webim.dto.Response;
import com.zzitbar.webim.dto.UserDto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * WebSocket 消息处理器
 */
@Component
@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 收到消息回调方法
     * @param ctx
     * @param frame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
            // 1. 加入群聊
            // 2. 群发通知有新人加入
            // 3.
            Message message = JSON.parseObject(request, Message.class);
            message.setCreateTime(new Date());
            UserDto userDto = CacheUtil.getCache().getUser(message.getUserId());
            message.setUserDto(userDto);
            if (null == userDto) {
                return;
            }
            if (Constants.MESSAGE_COMMAND.CONNECTED.getValue().equals(message.getCommand())) {
                // 10：连接成功
                CacheUtil.getCache().addChannel(message.getUserId(), ctx.channel());
                sendGroupInfo(message, ctx.channel());
            } else if (Constants.MESSAGE_COMMAND.JOIN_GROUP.getValue().equals(message.getCommand())) {
                // 20：加入群聊
                CacheUtil.getCache().joinGroup(message.getGroupId(), userDto.getUserId());
                sendMsg(message, ctx);
                sendGroupInfo(message, ctx.channel());
            } else if (Constants.MESSAGE_COMMAND.QUIT_GROUP.getValue().equals(message.getCommand())) {
                // 30：退出群聊
                CacheUtil.getCache().quitGroup(message.getGroupId(), userDto.getUserId());
                sendMsg(message, ctx);
                sendGroupInfo(message, ctx.channel());
            } else if (Constants.MESSAGE_COMMAND.CHAT.getValue().equals(message.getCommand())) {
                sendMsg(message, ctx);
            }
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }

    /**
     * 新用户加入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        Channel incoming = ctx.channel();
//        for (Channel channel : channels) {
//            channel.writeAndFlush(new TextWebSocketFrame("[新用户] - " + " 加入"));
//        }
//        redisTemplate.save(incoming.id(),uName);   //存储用户
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
//        //用户离开
//        for (Channel channel : channels) {
//            channel.writeAndFlush(new TextWebSocketFrame("[用户] - "  + " 离开"));
//        }
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:"+"在线");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:"+"掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + "异常");
        cause.printStackTrace();
//        ctx.close();
    }

    private void sendGroupInfo(Message message, Channel channel) {
        // ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        // 返回所有群组信息
        List<GroupDto> groupDtos = CacheUtil.getCache().groupList();
        groupDtos.stream().forEach(e -> {
            Set<Channel> channels = CacheUtil.getCache().groupChannels(e.getGroupId());
            if (null != channels && channels.contains(channel)) {
                e.setContainsMe(1);
            } else {
                e.setContainsMe(0);
            }
        });
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(Response.groupList(groupDtos))));
    }
    private void sendMsg(Message message, ChannelHandlerContext ctx) {
        //发送消息
        Set<Channel> channels = CacheUtil.getCache().groupChannels(message.getGroupId());
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(Response.message(message))));
        }
    }
}