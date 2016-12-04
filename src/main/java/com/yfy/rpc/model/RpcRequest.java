package com.yfy.rpc.model;

import java.lang.reflect.Method;

public class RpcRequest {
  public Method method;
  public Object[] args;

  public RpcRequest(Method method, Object[] args) {
    this.method = method;
    this.args = args;
  }
}
