package com.yfy.rpc.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import io.netty.buffer.ByteBuf;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.util.Collections;

/**
 * Created by yfy on 16-12-4.
 */
public class Util {
  public static void log(Object obj) {
    System.out.println(obj);
  }

  public static void serialize(Object obj, ByteBuf out) {
    Kryo kryo = new Kryo();
    //kryo.addDefaultSerializer(Throwable.class, new JavaSerializer());
    Output output = new Output(1024, Integer.MAX_VALUE);
    kryo.writeObject(output, obj);
    Util.log("encode(" + output.position() + "): " + obj);
    out.writeInt(output.position());
    out.writeBytes(output.toBytes());
  }

  public static Object deserialize(ByteBuf in, Class clazz) {
    Kryo kryo = new Kryo();
    kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
    byte[] bytes = new byte[in.readableBytes()];
    in.readBytes(bytes);
    Input input = new Input(bytes);
    Object obj = kryo.readObject(input, clazz);
    Util.log("decode(" + bytes.length + "): " + obj);
    return obj;
  }
}
