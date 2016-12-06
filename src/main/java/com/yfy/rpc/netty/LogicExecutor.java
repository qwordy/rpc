package com.yfy.rpc.netty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yfy on 16-12-6.
 */
public class LogicExecutor {
  public static ExecutorService executor = Executors.newFixedThreadPool(
      Runtime.getRuntime().availableProcessors());

  public static void run(Runnable r) {
    executor.execute(r);
  }
}
