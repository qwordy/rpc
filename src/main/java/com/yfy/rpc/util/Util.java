package com.yfy.rpc.util;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

  public static Object deserialize(ByteBuf in, Class<?> clazz) {
    Kryo kryo = new Kryo();
    kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
    byte[] bytes = new byte[in.readableBytes()];
    in.readBytes(bytes);
    Input input = new Input(bytes);
    Object obj = kryo.readObject(input, clazz);
    Util.log("decode(" + bytes.length + "): " + obj);
    return obj;
  }

  public static void hessianSerialize(Object obj, ByteBuf out) throws Exception {
    //Util.log("encode");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    HessianFactory factory = new HessianFactory();
    Hessian2Output output = factory.createHessian2Output(bos);
    //output.startMessage();
    output.writeObject(obj);
    //output.completeMessage();
    output.close();
    Util.log("encode(" + bos.size() + "): " + obj);
    out.writeInt(bos.size());
    out.writeBytes(bos.toByteArray());
  }

  public static Object hessianDeserialize(ByteBuf in, Class<?> clazz) throws Exception {
    //Util.log("decode");
    byte[] bytes = new byte[in.readableBytes()];
    in.readBytes(bytes);
    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
    HessianFactory factory = new HessianFactory();
    Hessian2Input input = factory.createHessian2Input(bin);
    //input.startMessage();
    Object obj = input.readObject(clazz);
    //input.completeMessage();
    input.close();
    Util.log("decode(" + bytes.length + "): " + obj);
    return obj;
  }
}
