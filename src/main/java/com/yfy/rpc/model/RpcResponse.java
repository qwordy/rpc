package com.yfy.rpc.model;

public class RpcResponse {
  private String errorMsg;

  private Object result;

  public RpcResponse(Object result, String errorMsg) {
    this.result = result;
    this.errorMsg = errorMsg;
  }

  @Override
  public String toString() {
    return result + " " + errorMsg;
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
