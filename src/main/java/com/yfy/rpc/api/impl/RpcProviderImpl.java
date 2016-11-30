package com.yfy.rpc.api.impl;

import com.yfy.rpc.api.RpcProvider;

/**
 * Created by yfy on 16-11-30.
 */
public class RpcProviderImpl extends RpcProvider {
  public RpcProviderImpl() {
    super();
  }

  @Override
  public RpcProvider serviceInterface(Class<?> serviceInterface) {
    return super.serviceInterface(serviceInterface);
  }

  @Override
  public RpcProvider version(String version) {
    return super.version(version);
  }

  @Override
  public RpcProvider impl(Object serviceInstance) {
    return super.impl(serviceInstance);
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
