package com.yfy.rpc.netty;

import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.util.Util;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

import java.nio.charset.Charset;

/**
 * Created by yfy on 16-12-3.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

//  private ChannelHandlerContext ctx;

  //private final byte[] lock = new byte[0];

//  @Override
//  protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
//
//  }

//  @Override
//  public void channelActive(ChannelHandlerContext ctx) throws Exception {
//    //Util.log(Thread.currentThread());
//    Thread.sleep(100);
//    this.ctx = ctx;
//    synchronized (lock) {
//      lock.notify();
//    }
//    ctx.writeAndFlush(new RpcRequest());//.await();
//  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);
  }

//  public void send(Object msg) throws Exception {
////    if (ctx == null) {
////      synchronized (lock) {
////        if (ctx == null) lock.wait();
////      }
////    }
//    Util.log("send: " + msg);
//    //Util.log(Thread.currentThread());
//    ctx.writeAndFlush(msg).await();
//  }
}
