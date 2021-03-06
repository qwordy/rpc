package com.yfy.rpc.api;

import com.yfy.rpc.model.RpcRequest;
import com.yfy.rpc.model.RpcResponse;
import com.yfy.rpc.netty.RpcServer;

import java.lang.reflect.Method;

public class RpcProvider {

  private Class<?> serviceInterface;

  private Object serviceInstance;

  private String version, classSig;

  public RpcProvider() {
  }

  public String getClassSig() {
    return classSig;
  }

  public RpcResponse invoke(RpcRequest msg) {
    Method[] methods = serviceInterface.getMethods();
    Method m0 = null;
    for (Method m : methods) {
      if (m.getName().equals(msg.methodName)) {
        m0 = m;
        break;
      }
    }
    if (m0 == null)
      return new RpcResponse(msg.id, null, "NoSuchMethod");
    try {
      Object ret = m0.invoke(serviceInstance, msg.args);
      return new RpcResponse(msg.id, ret, null);
    } catch (Exception e) {
      return new RpcResponse(msg.id, e, "exception");
    }
  }

  /**
   * set the interface which this provider want to expose as a service
   *
   * @param serviceInterface
   */
  public RpcProvider serviceInterface(Class<?> serviceInterface) {
    this.serviceInterface = serviceInterface;
    return this;
  }

  /**
   * set the version of the service
   *
   * @param version
   */
  public RpcProvider version(String version) {
    this.version = version;
    return this;
  }

  /**
   * set the instance which implements the service's interface
   *
   * @param serviceInstance
   */
  public RpcProvider impl(Object serviceInstance) {
//    serviceInterface.cast(serviceInstance);
    this.serviceInstance = serviceInstance;
    return this;
  }

  /**
   * set the timeout of the service
   *
   * @param timeout
   */
  public RpcProvider timeout(int timeout) {
    //TODO
    return this;
  }

  /**
   * set serialize type of this service
   *
   * @param serializeType
   */
  public RpcProvider serializeType(String serializeType) {
    //TODO
    return this;
  }

  /**
   * publish this service
   * if you want to publish your service , you need a registry server.
   * after all , you cannot write servers' ips in config file when you have 1 million server.
   * you can use ZooKeeper as your registry server to make your services found by your consumers.
   */
  public void publish() {
    classSig = serviceInterface.getName() + ' ' + version;
    RpcServer.instance().register(this);
  }
}
