package com.yfy.rpc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by yfy on 16-12-3.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
//  @Override
//  public void channelActive(ChannelHandlerContext ctx) throws Exception {
//    System.out.println("channel active");
//    System.out.println(this);
//    System.out.println(ctx);
//  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.print(((ByteBuf) msg).toString(Charset.defaultCharset()));
  }
}
