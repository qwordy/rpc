package com.yfy.rpc.api.impl;

import com.yfy.rpc.api.RpcProvider;

/**
 * Created by yfy on 16-11-30.
 */
public class RpcProviderImpl extends RpcProvider {
  private Class<?> serviceInterface;

  private String version;

  public RpcProviderImpl() {
    super();
  }

  @Override
  public RpcProvider serviceInterface(Class<?> serviceInterface) {
    this.serviceInterface = serviceInterface;
    return this;
  }

  @Override
  public RpcProvider version(String version) {
    this.version = version;
    return this;
  }

  @Override
  public RpcProvider impl(Object serviceInstance) {

    return this;
  }

  @Override
  public RpcProvider timeout(int timeout) {
    return super.timeout(timeout);
  }

  @Override
  public RpcProvider serializeType(String serializeType) {
    return super.serializeType(serializeType);
  }

  @Override
  public void publish() {
    super.publish();
  }
}
