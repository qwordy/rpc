package com.yfy.rpc.netty;

import com.yfy.rpc.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

/**
 * Created by yfy on 16-12-4.
 */
public class RequestDecoder extends LengthFieldBasedFrameDecoder {
  public RequestDecoder() {
    super(Integer.MAX_VALUE, 0, 4);
  }

  @Override
  protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    Util.log("decode: " + in.toString(CharsetUtil.UTF_8));
    ByteBuf buf = (ByteBuf) super.decode(ctx, in);
    if (buf == null) return null;
    return buf;
  }
}
