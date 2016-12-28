package com.yfy.rpc.api;

import com.yfy.rpc.aop.ConsumerHook;
import com.yfy.rpc.async.ResponseCallbackListener;
import com.yfy.rpc.async.ResponseFuture;
import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.netty.ClientHandler;
import com.yfy.rpc.netty.RpcClient;
import com.yfy.rpc.util.Util;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcConsumer implements InvocationHandler {
  private Class<?> interfaceClazz;

  private Channel channel;

  private String version, asynMethod;

  private int timeout;

  private AtomicInteger requestId = new AtomicInteger();

  private Map<Integer, Object> map = new ConcurrentHashMap<>();

  private ResponseCallbackListener listener;

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
    timeout = clientTimeout;
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
    asynMethod = methodName;
    listener = callbackListener;
  }

  public void cancelAsyn(String methodName) {
    asynMethod = null;
    listener = null;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    int id = requestId.getAndIncrement();
    RpcRequest request = new RpcRequest(id, null, method.getName(), args);
    map.put(id, request);
    channel.writeAndFlush(request);
    if (method.getName().equals(asynMethod)) {
      FutureTask<Object> futureTask = new FutureTask<>(new Callable<Object>() {
        @Override
        public Object call() throws Exception {
          synchronized (request) {
            while (map.get(id) instanceof RpcRequest)
              request.wait();
          }
          RpcResponse rpcResponse = (RpcResponse) map.get(id);
          if (listener != null)
            listener.onResponse(rpcResponse.getAppResponse());
          return map.get(id);
        }
      });
      new Thread(futureTask).start();
      ResponseFuture.futureThreadLocal.set(futureTask);
      return null;
    }
    synchronized (request) {
      while (map.get(id) instanceof RpcRequest) {
        long time0 = System.currentTimeMillis();
        request.wait(timeout);
        long time = System.currentTimeMillis() - time0;
        if (time >= timeout) throw new Exception("timeout");
      }
    }
    RpcResponse response = ((RpcResponse)map.get(id));
    if (response.isError()) {
      try {
        throw (Throwable) response.result;
      } catch (InvocationTargetException e) {
        throw e.getCause();
      }
    }
    map.remove(id);
    return response.result;
  }

  public Map<Integer, Object> getMap() {
    return map;
  }
}

