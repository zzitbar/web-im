package com.zzitbar.webim;

import com.zzitbar.webim.netty.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebImApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(WebImApplication.class, args);
        WebSocketServer server = context.getBean(WebSocketServer.class);
        server.start();
    }

}
