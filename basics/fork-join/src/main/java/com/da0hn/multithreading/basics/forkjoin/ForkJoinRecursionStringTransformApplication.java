package com.da0hn.multithreading.basics.forkjoin;

import com.da0hn.multithreading.commons.data.SimpleDataSet;
import com.da0hn.multithreading.commons.utils.CommonUtil;
import com.da0hn.multithreading.commons.utils.LoggerUtil;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ForkJoinRecursionStringTransformApplication extends RecursiveTask<List<String>> {

  @Serial
  private static final long serialVersionUID = -2428623236759617882L;
  private static final int PROCESSOR_DELAY_IN_MS = 500;

  private List<String> inputList;

  private ForkJoinRecursionStringTransformApplication(final List<String> inputList) {
    this.inputList = new ArrayList<>(inputList);
  }

  public static void main(final String[] args) {
    CommonUtil.startTimer();
    final var names = SimpleDataSet.namesList();

    final ForkJoinPool forkJoinPool = new ForkJoinPool();
    final ForkJoinRecursionStringTransformApplication transformerTask = new ForkJoinRecursionStringTransformApplication(names);
    final var processedNames = forkJoinPool.invoke(transformerTask);
    CommonUtil.timeElapsed();
    LoggerUtil.log("Final result: " + processedNames);
  }

  private static String addNameLengthTransform(final String name) {
    CommonUtil.delay(PROCESSOR_DELAY_IN_MS);
    return name.length() + " - " + name;
  }

  @Override
  protected List<String> compute() {
    if (this.inputList.size() <= 1) {
      return this.inputList.stream()
        .map(ForkJoinRecursionStringTransformApplication::addNameLengthTransform)
        .collect(Collectors.toList());
    }
    final int midpoint = this.inputList.size() / 2;
    final var firstHalfTask = new ForkJoinRecursionStringTransformApplication(
      this.inputList.subList(0, midpoint)
    ).fork();
    this.inputList = this.inputList.subList(midpoint, this.inputList.size());
    final var secondHalfResult = this.compute();
    final var firstHalfResult = firstHalfTask.join();
    return Stream.concat(secondHalfResult.stream(), firstHalfResult.stream())
      .collect(Collectors.toList());
  }

}
