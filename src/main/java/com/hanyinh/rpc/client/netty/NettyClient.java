package com.hanyinh.rpc.client.netty;

import com.hanyinh.rpc.common.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端启动监听器
 * Netty方式
 * @author Hanyinh
 * @date 2020/5/8 16:41
 */
@Slf4j
public class NettyClient {

    public Object start(String host, Integer port, RpcRequest rpcRequest) {
        ClientChannelInitializer initializer = new ClientChannelInitializer(new NettyClientHandler());

        // new 一个线程组
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(initializer)
                // 该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true);

        // 发起连接
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            log.error("客户端连接成功");
            // 发送消息

            future.channel().writeAndFlush("你好，我是客户端！").sync();
            future.channel().writeAndFlush(rpcRequest).sync();
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程组
            group.shutdownGracefully();
        }
        return initializer.getResponse();
    }
}
