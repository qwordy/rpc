package com.yfy.rpc.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;

/**
 * Created by yfy on 16-12-4.
 */
public class Util {
  public static void log(Object obj) {
    System.out.println(obj);
  }

  public static void serialize(Object obj, ByteBuf out) {
    Kryo kryo = new Kryo();
    Output output = new Output(1024, Integer.MAX_VALUE);
    kryo.writeObject(output, obj);
    Util.log("encode(" + output.position() + "): " + obj);
    out.writeInt(output.position());
    out.writeBytes(output.toBytes());
  }
}
