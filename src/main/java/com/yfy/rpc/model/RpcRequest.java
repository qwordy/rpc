package com.yfy.rpc.model;

import java.util.Arrays;

public class RpcRequest {
  public String classSig; // class + version
  public String methodName;
  public Object[] args;

  public RpcRequest() {}

  public RpcRequest(String classSig, String methodName, Object[] args) {
    this.classSig = classSig;
    this.methodName = methodName;
    this.args = args;
  }

  @Override
  public String toString() {
    return classSig + ' ' + methodName + ' ' + Arrays.toString(args);
  }
}
