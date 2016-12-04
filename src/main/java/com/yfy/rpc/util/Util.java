package com.yfy.rpc.util;

/**
 * Created by yfy on 16-12-4.
 */
public class Util {
  public static void log(Object obj) {
    System.out.println(obj);
  }

  public static byte[] int2byte(int n) {
    byte[] b = new byte[4];
    b[3] = (byte) (n >> 24);
    b[2] = (byte) (n >> 16);
    b[1] = (byte) (n >> 8);
    b[0] = (byte) (n);
    return b;
  }
}
