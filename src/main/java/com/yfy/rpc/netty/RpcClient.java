package com.yfy.rpc.netty;

import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.util.Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * Created by yfy on 16-12-3.
 */
public class RpcClient {
  public static void main(String[] args) throws Exception {
    startClient();
  }

  public static Channel startClient() {
    ClientHandler handler = new ClientHandler();
    NioEventLoopGroup group = new NioEventLoopGroup();
    Bootstrap b = new Bootstrap();
    b.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new ResponseDecoder())
                .addLast(new RequestEncoder()).addLast(handler);
          }
        });
    //.option(ChannelOption.TCP_NODELAY, true);
    try {
      Channel channel = b.connect("localhost", 8888).sync().channel();
      if (channel == null) Util.log("null");
      //Util.log(Thread.currentThread());
      //channel.writeAndFlush(new RpcRequest());
      return channel;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      group.shutdownGracefully();
    }
  }
}
