package com.da0hn.multithreading.commons.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimpleDataSet {

  public static List<String> lowerCaseAlphabetList = List.of("a", "b", "c", "a", "d", "e", "f", "e", "g", "h", "i");

  public static List<String> namesList() {
    return List.of("Bob", "Jamie", "Jill", "Rick");

  }

  public static List<Integer> generateIntegerList(final int maxNumber) {
    return IntStream.rangeClosed(1, maxNumber)
      .boxed().collect(Collectors.toList());
  }

  public static ArrayList<Integer> generateArrayList(final int maxNumber) {
    final ArrayList<Integer> arrayList = new ArrayList<>();

    IntStream.rangeClosed(1, maxNumber)
      .boxed()
      .forEach((arrayList::add));
    return arrayList;
  }

  public static LinkedList<Integer> generateIntegerLinkedList(final int maxNumber) {
    final LinkedList<Integer> linkedList = new LinkedList<>();
    IntStream.rangeClosed(1, maxNumber)
      .boxed()
      .forEach((linkedList::add));
    return linkedList;
  }

  public static Set<Integer> generateIntegerSet(final int maxNumber) {
    return IntStream.rangeClosed(1, maxNumber)
      .boxed().collect(Collectors.toSet());
  }


  public static double generateRandomPrice() {
    final int min = 50;
    final int max = 100;
    return Math.random() * (max - min + 1) + min;
  }

}
