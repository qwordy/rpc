package com.yfy.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by yfy on 16-12-3.
 */
public class RpcClient {
  public static void main(String[] args) throws Exception {
    startNewClient();
  }

  public static ClientHandler startNewClient(){
    ClientHandler handler = new ClientHandler();
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    b.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(handler);
          }
        });
    try {
      b.connect("localhost", 8888).sync();//.addListener(future -> handler.send("hello"));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return handler;
  }
}
