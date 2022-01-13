package com.tgt.netty.client;


import com.tgt.tpa.netty.ClientSocketHandlerObserver;
import com.tgt.tpa.netty.ClientSocketHandlerSubject;
import com.tgt.tpa.netty.GenericSocketClient;
import com.tgt.tpa.netty.GenericSocketInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
//@Scope(value = "prototype")
public class CustomHandler extends ClientSocketHandlerObserver {

    private static final Logger logger = LoggerFactory.getLogger(CustomHandler.class);
    private CountDownLatch socketResponseCounter = new CountDownLatch(1);

    @Autowired
    GenericSocketClient client;

    public void sendMessage(String message) {
        Channel channel = null;
        GenericSocketInboundHandler inboundHandler = new ClientSocketHandlerSubject(this);
        try {
            channel = client.getAvailableChannel();
            if (null != channel) {
                channel.pipeline().addLast("netty-client", inboundHandler);
                channel.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
                socketResponseCounter.await(15, TimeUnit.SECONDS);
            } else {
                throw new Exception("No server channel available");
            }
        } catch (Exception e) {
                e.printStackTrace();
        } finally {
            if (channel != null && channel.pipeline().toMap().containsKey("netty-client")) {
                channel.pipeline().remove("netty-client");
            }
        }

    }

    @Override
    public void messageReceived(byte[] response) {
        socketResponseCounter.countDown();
        String received = new String(response, CharsetUtil.UTF_8);
        System.out.println("Response received 2 GenericSocketClient" + received);

    }

}
