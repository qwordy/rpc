package com.yfy.rpc.api.impl;

import com.yfy.rpc.aop.ConsumerHook;
import com.yfy.rpc.api.RpcConsumer;
import com.yfy.rpc.async.ResponseCallbackListener;

import java.lang.reflect.Method;

/**
 * Created by yfy on 16-11-30.
 */
public class RpcConsumerImpl extends RpcConsumer {
  public RpcConsumerImpl() {
    super();
  }

  @Override
  public RpcConsumer interfaceClass(Class<?> interfaceClass) {
    return super.interfaceClass(interfaceClass);
  }

  @Override
  public RpcConsumer version(String version) {
    return super.version(version);
  }

  @Override
  public RpcConsumer clientTimeout(int clientTimeout) {
    return super.clientTimeout(clientTimeout);
  }

  @Override
  public RpcConsumer hook(ConsumerHook hook) {
    return super.hook(hook);
  }

  @Override
  public Object instance() {
    return super.instance();
  }

  @Override
  public void asynCall(String methodName) {
    super.asynCall(methodName);
  }

  @Override
  public <T extends ResponseCallbackListener> void asynCall(String methodName, T callbackListener) {
    super.asynCall(methodName, callbackListener);
  }

  @Override
  public void cancelAsyn(String methodName) {
    super.cancelAsyn(methodName);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    return super.invoke(proxy, method, args);
  }
}
