package com.yfy.rpc.netty;

import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yfy on 16-12-4.
 */
public class ResponseEncoder extends MessageToByteEncoder<RpcResponse> {
  @Override
  protected void encode(ChannelHandlerContext ctx, RpcResponse msg, ByteBuf out) throws Exception {
    Util.hessianSerialize(msg, out);
  }
}
