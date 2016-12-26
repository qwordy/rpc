package com.yfy.rpc.model;

import java.io.Serializable;

public class RpcResponse implements Serializable {
  public int id;
  public Object result;
  public String errorMsg;

  public RpcResponse() {}

  public RpcResponse(int id, Object result, String errorMsg) {
    this.id = id;
    this.result = result;
    this.errorMsg = errorMsg;
  }

  @Override
  public String toString() {
    return "result:" + result + " errorMsg:" + errorMsg;
  }

  public Object getAppResponse() {
    return result;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public boolean isError() {
    return errorMsg != null;
  }
}
