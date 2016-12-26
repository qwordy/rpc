package com.yfy.rpc.netty;

import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by yfy on 16-12-4.
 */
public class RequestDecoder extends ByteToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    out.add(Util.hessianDeserialize(in, RpcRequest.class));
  }
}
