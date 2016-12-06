package com.yfy.rpc.netty;

import com.yfy.rpc.api.RpcProvider;
import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yfy on 16-12-3.
 */
public class ServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
  private RpcProvider provider;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    Util.log("client connected: " + ctx.channel().remoteAddress());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    Util.log("channel inactive");
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
    //Util.log("read: " + msg);
    if (provider == null) {
      if (msg.classSig != null) {
        provider = RpcServer.instance().getRpcProvider(msg.classSig);
        if (provider == null) ctx.close();
      } else {
        ctx.close();
      }
    } else {
      ctx.writeAndFlush(provider.invoke(msg));
//      LogicExecutor.run(() -> ctx.writeAndFlush(provider.invoke(msg)));
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
