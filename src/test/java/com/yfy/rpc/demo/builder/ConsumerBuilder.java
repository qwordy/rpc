package com.yfy.rpc.demo.builder;

import com.yfy.rpc.api.RpcConsumer;
import com.yfy.rpc.async.ResponseFuture;
import com.yfy.rpc.context.RpcContext;
import com.yfy.rpc.demo.service.*;
import com.yfy.rpc.util.Util;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConsumerBuilder {
  private static RpcConsumer consumer;
  private static RaceTestService apiService;

  static {
    try {
      consumer = (RpcConsumer) getConsumerImplClass().newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    if (consumer == null) {
      System.out.println("Start rpc consumer failed");
      System.exit(1);
    }
    apiService = (RaceTestService) consumer
        .interfaceClass(RaceTestService.class)
        .version("1.0.0.api")
        .clientTimeout(3000)
        .hook(new RaceConsumerHook()).instance();

  }

  public boolean pressureTest() {
    try {
      RaceDO result = apiService.getDO();
      if (result == null)
        return false;
    } catch (Throwable t) {
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    ConsumerBuilder builder = new ConsumerBuilder();
    builder.testNormalApiCall();
    builder.testNormalSpringCall();
    builder.testTimeoutCall();
    builder.testCatchException();
    builder.testFutureCall();
    builder.testCallback();
    builder.testRpcContext();
    Util.log("Pass all tests!");
  }

  @Test
  public void testNormalApiCall() {
//    apiService.addOne(4);
    Assert.assertEquals(5, apiService.addOne(4));
    List<String> list = new ArrayList<>();
    list.add("123");
    Assert.assertEquals("end", apiService.getList(list).get(0));
    Assert.assertNotNull(apiService.getMap());
    Assert.assertEquals("this is a rpc framework", apiService.getString());
    Assert.assertEquals(new RaceDO(), apiService.getDO());
  }

  @Test
  public void testNormalSpringCall() {
    Assert.assertNotNull(apiService.getMap());
    Assert.assertEquals("this is a rpc framework", apiService.getString());
    Assert.assertEquals(new RaceDO(), apiService.getDO());
  }

  /**
   * test timeout property,at init this service,we set the client timeout 3000ms
   * so we should break up before Provider finish running(Provider will sleep 5000ms beyond clientTimeout)
   */
  @Test
  public void testTimeoutCall() {
    long beginTime = System.currentTimeMillis();
    try {
      boolean result = apiService.longTimeMethod();
      Assert.fail();
    } catch (Exception e) {
      long period = System.currentTimeMillis() - beginTime;
      Assert.assertTrue(period < 3100);
    }
  }

  /**
   * when provider throws an exception when process the request,
   * the rpc framework must pass the exception to the consumer
   */
  @Test
  public void testCatchException() {
    try {
      Integer result = apiService.throwException();
      Assert.fail();
    } catch (RaceException e) {
      Assert.assertEquals("race", e.getFlag());
      //e.printStackTrace();
    }
  }

  /**
   * set the method {@code getString} of {@link RaceTestService} asynchronous
   * and get response with the tool {@link ResponseFuture} based on ThreadLocal
   */
  @Test
  public void testFutureCall() {
    consumer.asynCall("getString");
    String nullValue = apiService.getString();
    Assert.assertEquals(null, nullValue);

    try {
      String result = (String) ResponseFuture.getResponse(3000);
      Assert.assertEquals("this is a rpc framework", result);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      consumer.cancelAsyn("getString");
    }
  }

  /**
   * set the method {@code getDO} of {@link RaceTestService} asynchronous
   * and pass a listener to framework,when response is back
   * we want our listener be called by framework
   */
  @Test
  public void testCallback() {
    RaceServiceListener listener = new RaceServiceListener();
    consumer.asynCall("getDO", listener);
    RaceDO nullDo = apiService.getDO();
    Assert.assertEquals(null, nullDo);
    try {
      RaceDO resultDo = (RaceDO) listener.getResponse();
    } catch (InterruptedException e) {
    } finally {
      consumer.cancelAsyn("getDO");
    }
  }

  /**
   * use {@link RpcContext} to pass a key-value structure to Provider
   * {@function getMap()} will pass this context to Consumer
   */
  @Test
  public void testRpcContext() {
    RpcContext.addProp("context", "please pass me!");
    Map<String, Object> resultMap = apiService.getMap();
    Assert.assertTrue(resultMap.containsKey("context"));
    Assert.assertTrue(resultMap.containsValue("please pass me!"));
  }

  /**
   * I can run a hook before I try to call a remote service.
   */
  @Test
  public void testConsumerHook() {
    Map<String, Object> resultMap = apiService.getMap();
    Assert.assertTrue(resultMap.containsKey("hook key"));
    Assert.assertTrue(resultMap.containsValue("this is pass by hook"));
  }

  private static Class<?> getConsumerImplClass() {
    try {
      return Class.forName("com.yfy.rpc.api.RpcConsumer");
    } catch (ClassNotFoundException e) {
      System.out.println("Cannot found the class which must exist and override all RpcProvider's methods");
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }
}
