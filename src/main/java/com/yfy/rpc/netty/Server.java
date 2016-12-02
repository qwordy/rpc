package com.yfy.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yfy on 16-12-2.
 */
public class Server {
  public static void main(String[] args) throws Exception {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ServerInitializer())
        .option(ChannelOption.SO_BACKLOG, 5)
        .childOption(ChannelOption.SO_KEEPALIVE, true);
    b.bind(8888).sync();
  }

}
