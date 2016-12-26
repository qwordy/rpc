package com.yfy.rpc.model;

import java.io.Serializable;
import java.util.Arrays;

public class RpcRequest implements Serializable {
  public int id;
  public String classSig; // class + version
  public String methodName;
  public Object[] args;

  public RpcRequest() {}

  public RpcRequest(int id, String classSig, String methodName, Object[] args) {
    this.id = id;
    this.classSig = classSig;
    this.methodName = methodName;
    this.args = args;
  }

  @Override
  public String toString() {
    return "classSig:" + classSig + " method:" + methodName + " args:" + Arrays.toString(args);
  }
}
