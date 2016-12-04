package com.yfy.rpc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by yfy on 16-12-3.
 */
public class RpcServer {
  public static void main(String[] args) throws Exception {
    startNewServer();
  }

  public static void startNewServer() {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ServerHandler());
          }
        });
//        .option(ChannelOption.SO_BACKLOG, 5)
//        .childOption(ChannelOption.SO_KEEPALIVE, true);
    try {
      b.bind(8888).sync();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
