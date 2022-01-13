package com.tgt.netty.client;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestMessageSender {

    @Autowired
    private ObjectFactory<CustomHandler> handler;

    @PostConstruct
    @Scheduled(fixedDelay=500)
    public void sendTestMessage()
    {
        System.out.println("----- Request sent -----");
        String msg = "------ Request -------";
        handler.getObject().sendMessage(msg);
    }
}
