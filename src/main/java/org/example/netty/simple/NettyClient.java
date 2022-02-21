package org.example.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 996Worker
 * @description
 * @create 2022-02-21 15:52
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        // 客户端需要一个时间循环组

        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        // 客户端使用Bootstrap启动对象, 不是ServerBootStrap
        Bootstrap bootstrap = new Bootstrap();

        try {
            // 设置相关参数
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class) // 客户端Channel实现
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MyNettyClientHandler()); // 定义handler逻辑
                        }
                    });

            System.out.println("客户端Ok");
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            channelFuture.channel().closeFuture().sync();

        } finally {
            eventExecutors.shutdownGracefully();
        }





    }

}