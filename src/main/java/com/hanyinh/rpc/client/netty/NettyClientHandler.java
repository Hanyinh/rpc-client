package com.hanyinh.rpc.client.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * netty客户端处理器
 *
 * @author Hanyinh
 * @date 2020/5/8 16:32
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Object response;

    /**
    * 服务端连接会触发
    * @author Hanyinh
    * @date 2020/5/8 16:33
    * @param ctx
    * @return void
    */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.error("====================客户端Active====================");
    }

    /**
    * 服务端发送消息会触发
    * @author Hanyinh
    * @date 2020/5/8 16:34
    * @param ctx
    * @param msg
    * @return void
    */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.error("客户端收到消息：{}", JSON.toJSONString(msg));
        response = msg;
    }

    /**
    * 发生异常会触发
    * @author Hanyinh
    * @date 2020/5/8 16:36
    * @param ctx
    * @param cause
    * @return void
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    Object getResponse() {
        return response;
    }
}
