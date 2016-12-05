package com.yfy.rpc.netty;

import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by yfy on 16-12-3.
 */
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
    Util.log("read: " + msg);
  }
}
