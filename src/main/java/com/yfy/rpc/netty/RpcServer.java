package com.yfy.rpc.netty;

import com.yfy.rpc.api.RpcProvider;
import com.yfy.rpc.util.Util;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yfy on 16-12-3.
 */
public class RpcServer {

  private static volatile RpcServer server;

  private Map<String, RpcProvider> map;

  public static RpcServer instance() {
    if (server == null) {
      synchronized (RpcServer.class) {
        if (server == null) server = new RpcServer();
      }
    }
    return server;
  }

  public void register(RpcProvider provider) {
    map.put(provider.getClassSig(), provider);
  }

  public RpcProvider getRpcProvider(String classSig) {
    return map.get(classSig);
  }

  private RpcServer() {
    Util.log("start server");
    map = new HashMap<>();
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap b = new ServerBootstrap();
    b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4))
                .addLast(new RequestDecoder())
                .addLast(new ResponseEncoder())
                .addLast(new ServerHandler());
          }
        });
//        .option(ChannelOption.SO_BACKLOG, 5)
//        .childOption(ChannelOption.SO_KEEPALIVE, true);
    b.bind(8888).syncUninterruptibly();
  }

  public static void main(String[] args) throws Exception {
    instance();
  }

}
