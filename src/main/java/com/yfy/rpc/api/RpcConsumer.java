package com.yfy.rpc.api;

import com.yfy.rpc.aop.ConsumerHook;
import com.yfy.rpc.async.ResponseCallbackListener;
import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.netty.ClientHandler;
import com.yfy.rpc.netty.RpcClient;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcConsumer implements InvocationHandler {
  private Class<?> interfaceClazz;

  private Channel channel;

  private String version;

  private AtomicInteger requestId = new AtomicInteger();

  private Map<Integer, Object> map = new ConcurrentHashMap<>();

  public RpcConsumer() {}

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
    this.version = version;
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
    channel = RpcClient.connect();
    ((ClientHandler)channel.pipeline().last()).setConsumer(this);
    String classSig = interfaceClazz.getName() + ' ' + version;
    channel.writeAndFlush(new RpcRequest(0, classSig, null, null));
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
    int id = requestId.getAndIncrement();
    RpcRequest request = new RpcRequest(id, null, method.getName(), args);
    map.put(id, request);
    channel.writeAndFlush(request);
    synchronized (request) {
      if (map.get(id) instanceof RpcRequest)
        request.wait();
    }
    Object result = ((RpcResponse)map.get(id)).result;
    map.remove(id);
    return result;
  }

  public Map<Integer, Object> getMap() {
    return map;
  }
}

