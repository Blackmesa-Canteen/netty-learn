package org.example.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 996Worker
 * @description
 * @create 2022-02-21 13:18
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        // create BossGroup and WorkerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // configuration
            ServerBootstrap boot = new ServerBootstrap();

            boot.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 服务端channel实现
                    .option(ChannelOption.SO_BACKLOG, 128) // thread queue size
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // keep connection alive
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // set handler for pipeline
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MyNettyServerHandler());
                        }
                    });
            System.out.println("Server is ready");
            // set port
            // 绑定一个端口并同步, 生成一个ChannelFuture对象
            ChannelFuture sync = boot.bind(6668).sync();
            System.out.println("server started at 6668");

            // 对关闭通道的监听
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}