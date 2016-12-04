package com.yfy.rpc.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by yfy on 16-12-2.
 */
public class Client {
  public static void main(String[] args) throws Exception {
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    b.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ClientChannelInitializer());
    b.connect("localhost", 8888).sync();
  }
}
