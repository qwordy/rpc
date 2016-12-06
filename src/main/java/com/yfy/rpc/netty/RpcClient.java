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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

/**
 * Created by yfy on 16-12-3.
 */
public class RpcClient {
  public static void main(String[] args) {
  }

  private static Bootstrap bootstrap;

  static {
    NioEventLoopGroup group = new NioEventLoopGroup();
    bootstrap = new Bootstrap();
    bootstrap.group(group)
        .channel(NioSocketChannel.class)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                .addLast(new ResponseDecoder())
                .addLast(new RequestEncoder())
                .addLast(new ClientHandler());
          }
        });
  }

  public static Channel connect() {
    return bootstrap.connect("localhost", 8989).syncUninterruptibly().channel();
  }
}
