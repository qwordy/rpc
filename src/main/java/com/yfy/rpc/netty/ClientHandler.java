package com.yfy.rpc.netty;

import com.yfy.rpc.api.RpcConsumer;
import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.util.Util;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * Created by yfy on 16-12-3.
 */
public class ClientHandler extends SimpleChannelInboundHandler<RpcResponse> {
  private RpcConsumer consumer;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
    //Util.log("read: " + msg);
    Map<Integer, Object> map = consumer.getMap();
    Object request = map.get(msg.id);
    map.put(msg.id, msg);
    synchronized (request) {
      request.notify();
    }
  }

  public void setConsumer(RpcConsumer consumer) {
    this.consumer = consumer;
  }
}
