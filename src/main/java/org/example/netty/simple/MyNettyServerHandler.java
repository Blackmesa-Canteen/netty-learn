package org.example.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author 996Worker
 * @description
 * @create 2022-02-21 13:35
 */

// 入站请求的handler
public class MyNettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取数据
    // ctx: 含有pipeLine, channel
    // msg: 客户端发送的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // msg to byteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("Received msg from: " + ctx.channel().remoteAddress());
        System.out.println("Client says: " + buf.toString(CharsetUtil.UTF_8));

    }

    // after数据读完
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, I've received your message.", CharsetUtil.UTF_8));
    }

    // 处理异常

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("An exception occurred, close the chan.");
        ctx.channel().close();
    }
}