package com.yfy.rpc.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.nio.charset.Charset;

/**
 * Created by yfy on 16-12-2.
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
      @Override
      public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello world", Charset.defaultCharset()));
      }

      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print(((ByteBuf) msg).toString(Charset.defaultCharset()));
        ctx.writeAndFlush(msg);
      }

    });
  }
}
