package com.yfy.rpc.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by yfy on 16-12-4.
 */
public class ResponseDecoder extends LengthFieldBasedFrameDecoder {
  public ResponseDecoder() {
    super(Integer.MAX_VALUE, 0, 4);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    ByteBuf buf = (ByteBuf) super.decode(ctx, in);
    if (buf == null) return null;
    return buf;
  }
}
