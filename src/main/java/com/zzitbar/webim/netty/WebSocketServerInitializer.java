package com.zzitbar.webim.netty;

import com.zzitbar.webim.config.NettyAccountConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.ssl.SslContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebSocketFrameHandler webSocketFrameHandler;
    @Autowired
    private NettyAccountConfig nettyAccountConfig;

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(nettyAccountConfig.getContextPath(), null, true));
//        pipeline.addLast(new WebSocketIndexPageHandler(nettyAccountConfig.getContextPath()));
        pipeline.addLast(webSocketFrameHandler);
    }
}