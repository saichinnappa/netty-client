package com.tgt.netty.client;

import com.tgt.tpa.netty.GenericSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NettyClientComponent {

    @Value("${initialChannelPoolSize}")
    private String initialConnPoolSize;

    @Value("${maxChannelPoolSize}")
    private String maxConnPoolSize;

    @Value("${serverIP}")
    private String serverIP;

    @Value("${serverPort}")
    private String serverPort;

//    @Bean
//    public void initializeChannelPool() throws IOException {
//        String host = "10.63.3.71";
//        int port = 8000;
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//        try {
//            Bootstrap b = new Bootstrap();
//            b.group(workerGroup);
//            b.channel(NioSocketChannel.class);
//            b.option(ChannelOption.SO_KEEPALIVE, true);
//            b.handler(new ChannelInitializer<SocketChannel>() {
//
//                @Override
//                public void initChannel(SocketChannel ch)
//                        throws Exception {
//                    ch.pipeline().addLast(
//                            new ClientHandler());
//                }
//            })
//            ;
//
//            ChannelFuture f = b.connect(host, port).sync();
//
//            f.channel().closeFuture().sync();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        finally {
//            workerGroup.shutdownGracefully();
//        }
//    }

    @Bean
    public GenericSocketClient genericSocketClient() throws IOException
    {
        GenericSocketClient genericSocketClient =  new GenericSocketClient();
        System.out.println(serverIP+","+serverPort);
        genericSocketClient.setInitialPoolSize(1);
        genericSocketClient.setMaxPoolSize(2);
        genericSocketClient.setRemoteIp(serverIP);
        genericSocketClient.setRemotePort(Integer.parseInt(serverPort));
        genericSocketClient.initializeChannelPool();
        return genericSocketClient;

    }
}
