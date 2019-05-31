package com.zzitbar.webim.netty;

import com.zzitbar.webim.config.NettyAccountConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A HTTP server which serves Web Socket requests at:
 *
 * http://localhost:8080/websocket
 */
@Component
public class WebSocketServer {

    @Autowired
    private NettyAccountConfig nettyAccountConfig;
    @Autowired
    private WebSocketServerInitializer webSocketServerInitializer;

    private Channel serverChannel;

    public void start() throws Exception {
        serverChannel =  bootstrap().bind(tcpPost()).sync().channel().closeFuture().sync().channel();
    }

    @PreDestroy
    public void stop() {
        serverChannel.close();
        serverChannel.parent().close();
    }

    public ServerBootstrap bootstrap(){
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(webSocketServerInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        for (ChannelOption option : keySet) {
            b.option(option, tcpChannelOptions.get(option));
        }
        return b;
    }

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup(nettyAccountConfig.getBossThread());
    }
    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup(){
        return new NioEventLoopGroup(nettyAccountConfig.getWorkerThread());
    }
    public InetSocketAddress tcpPost(){
        return new InetSocketAddress(nettyAccountConfig.getPort());
    }

    public Map<ChannelOption<?>, Object> tcpChannelOptions(){
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, nettyAccountConfig.isKeepalive());
        options.put(ChannelOption.SO_BACKLOG, nettyAccountConfig.getBacklog());
        return options;
    }

}