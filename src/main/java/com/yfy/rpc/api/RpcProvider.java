package com.yfy.rpc.api;

import com.yfy.rpc.netty.RpcServer;

/**
 * Created by huangsheng.hs on 2015/3/26.
 */
public class RpcProvider {

  private Class<?> serviceInterface;

  private Object serviceInstance;

  private String version;

  public RpcProvider() {
  }

  /**
   * init Provider
   */
  private void init() {
    //TODO
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
    //serviceInterface.cast(serviceInstance);
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
    RpcServer.startNewServer();
  }
}
