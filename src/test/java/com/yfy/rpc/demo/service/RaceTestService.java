package com.yfy.rpc.demo.service;

import java.util.List;
import java.util.Map;

public interface RaceTestService {
  public Map<String, Object> getMap();

  public String getString();

  public RaceDO getDO();

  public boolean longTimeMethod();

  public Integer throwException() throws RaceException;

  public int addOne(int n);

  public List<String> getList(List<String> list);
}
