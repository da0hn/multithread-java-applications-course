package com.da0hn.multithreading.basics.forkjoin;

import com.da0hn.multithreading.commons.data.SimpleDataSet;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

public final class BlockingStringTransformApplication {

  public static void main(final String[] args) {
    CommonUtil.startTimer();
    final List<String> processedName = new ArrayList<>();
    final var names = SimpleDataSet.namesList();

    names.forEach(name -> processedName.add(addNameLengthTransform(name)));

    CommonUtil.timeElapsed();
    LoggerUtil.log("Final result: " + processedName);
  }


  private static String addNameLengthTransform(final String name) {
    CommonUtil.delay(500);
    return name.length() + " - " + name;
  }


}
