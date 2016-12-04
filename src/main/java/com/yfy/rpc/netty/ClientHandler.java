package com.yfy.rpc.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by yfy on 16-12-3.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

  private ChannelHandlerContext ctx;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    System.out.println("active");
    this.ctx = ctx;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);
  }

  public void send(String msg) {
    System.out.println("send: " + msg);
    ctx.writeAndFlush(Unpooled.copiedBuffer(msg, Charset.defaultCharset()));
  }
}
