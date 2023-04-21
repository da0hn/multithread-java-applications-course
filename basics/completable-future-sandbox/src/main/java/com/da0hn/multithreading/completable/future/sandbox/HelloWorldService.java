package com.da0hn.multithreading.completable.future.sandbox;

import com.da0hn.multithreading.commons.utils.LoggerUtil;

import static com.da0hn.multithreading.commons.utils.CommonUtil.delay;

public class HelloWorldService {
  public String helloWorld() {
    delay(1000);
    LoggerUtil.log("inside Hello World delayed method");
    return "Hello World";
  }

  public String hello() {
    delay(1000);
    LoggerUtil.log("inside Hello delayed method");
    return "Hello";
  }

  public String world() {
    delay(1000);
    LoggerUtil.log("inside World delayed method");
    return "World!";
  }



}
