package org.example.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author 996Worker
 * @description 通信的数据用HttpObject封装了
 * @create 2022-02-22 17:29
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        // 判断msg是不是http request
        if (httpObject instanceof HttpRequest) {
            System.out.println("msg: " + httpObject.getClass());
            System.out.println("Client addr:" + channelHandlerContext.channel().remoteAddress());

            // 回复信息给client
            ByteBuf content = Unpooled.copiedBuffer("Hello, I'm TestServer", CharsetUtil.UTF_8);

            // 构造http响应
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            channelHandlerContext.writeAndFlush(response);
        }
    }
}