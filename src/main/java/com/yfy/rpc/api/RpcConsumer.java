package com.yfy.rpc.api;

import com.yfy.rpc.aop.ConsumerHook;
import com.yfy.rpc.async.ResponseCallbackListener;
import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.netty.RpcClient;
import com.yfy.rpc.util.Util;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcConsumer implements InvocationHandler {
  private Class<?> interfaceClazz;

  private Channel channel;

  public RpcConsumer() {
  }

  /**
   * init a rpc consumer
   */
  private void init() {
    //TODO
  }

  /**
   * set the interface which this consumer want to use
   * actually,it will call a remote service to get the result of this interface's methods
   *
   * @param interfaceClass
   * @return
   */
  public RpcConsumer interfaceClass(Class<?> interfaceClass) {
    this.interfaceClazz = interfaceClass;
    return this;
  }

  /**
   * set the version of the service
   *
   * @param version
   * @return
   */
  public RpcConsumer version(String version) {
    //TODO
    return this;
  }

  /**
   * set the timeout of the service
   * consumer's time will take precedence of the provider's timeout
   *
   * @param clientTimeout
   * @return
   */
  public RpcConsumer clientTimeout(int clientTimeout) {
    //TODO
    return this;
  }

  /**
   * register a consumer hook to this service
   *
   * @param hook
   * @return
   */
  public RpcConsumer hook(ConsumerHook hook) {
    return this;
  }

  /**
   * return an Object which can cast to the interface class
   *
   * @return
   */
  public Object instance() {
    channel = RpcClient.startClient();
    return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{this.interfaceClazz}, this);
  }

  /**
   * mark a async method,default future call
   *
   * @param methodName
   */
  public void asynCall(String methodName) {
    asynCall(methodName, null);
  }

  /**
   * mark a async method with a callback listener
   *
   * @param methodName
   * @param callbackListener
   */
  public <T extends ResponseCallbackListener> void asynCall(String methodName, T callbackListener) {
    //TODO
  }

  public void cancelAsyn(String methodName) {
    //TODO
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    RpcRequest request = new RpcRequest(method, args);
    Util.log(method.toGenericString());
    channel.writeAndFlush(request);
//    for (Method m : proxy.getClass().getDeclaredMethods())
//      System.out.println(m.getName());
    return 1;
  }
}

