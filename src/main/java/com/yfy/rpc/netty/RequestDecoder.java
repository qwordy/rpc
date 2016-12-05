package com.yfy.rpc.netty;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.yfy.rpc.model.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.util.List;

/**
 * Created by yfy on 16-12-4.
 */
public class RequestDecoder extends ByteToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    Kryo kryo = new Kryo();
    kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
    byte[] bytes = new byte[in.readableBytes()];
    in.readBytes(bytes);
    Input input = new Input(bytes);
    RpcRequest request = kryo.readObject(input, RpcRequest.class);
//    Object request = kryo.readClassAndObject(input);
    out.add(request);
  }
}
