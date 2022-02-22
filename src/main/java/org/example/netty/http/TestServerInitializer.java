package org.example.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author 996Worker
 * @description
 * @create 2022-02-22 17:20
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 向pipeline加入handler
        // 得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 加入编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        // 试驾入自定义handler
        pipeline.addLast("MyTestHttpServerHandler", new TestHttpServerHandler());

    }
}