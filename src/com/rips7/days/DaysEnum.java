package com.rips7.days;

import com.rips7.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public enum DaysEnum {
  DAY_1(Day1.class, "day1/", "example1and2", "part1and2", "part1and2", Utils::readLinesAsInt),
  DAY_2(Day2.class, "day2/", "example1and2", "part1and2", "part1and2", Utils::readLines),
  DAY_3(Day3.class, "day3/", "example1and2", "part1and2", "part1and2", Utils::readLines),
  DAY_4(Day4.class, "day4/", "example1and2", "part1and2", "part1and2", Utils::readLines),
  DAY_5(Day5.class, "day5/", "example1", "example2", "part1and2", "part1and2", Utils::readLines),
  DAY_6(Day6.class, "day6/", "example1", "example2", "part1and2", "part1and2", Utils::readLines);

  private final Class<? extends Day<?>> dayClass;

  private final String example1FileName;

  private final String example2FileName;

  private final String part1FileName;

  private final String part2FileName;

  private final Function<String, ?> argumentSupplier;

  DaysEnum(
      final Class<? extends Day<?>> dayClass,
      final String resourceDirectory,
      final String example1FileName,
      final String example2FileName,
      final String part1FileName,
      final String part2FileName,
      final Function<String, ?> argumentSupplier) {
    this.dayClass = dayClass;
    this.example1FileName = resourceDirectory + example1FileName;
    this.example2FileName = resourceDirectory + example2FileName;
    this.part1FileName = resourceDirectory + part1FileName;
    this.part2FileName = resourceDirectory + part2FileName;
    this.argumentSupplier = argumentSupplier;
  }

  DaysEnum(
      final Class<? extends Day<?>> dayClass,
      final String resourceDirectory,
      final String exampleFileName,
      final String part1FileName,
      final String part2FileName,
      final Function<String, ?> argumentSupplier) {
    this(dayClass, resourceDirectory, exampleFileName, exampleFileName, part1FileName, part2FileName, argumentSupplier);
  }

  public String getExample1FileName() {
    return example1FileName;
  }

  public String getExample2FileName() {
    return example2FileName;
  }

  public String getPart1FileName() {
    return part1FileName;
  }

  public String getPart2FileName() {
    return part2FileName;
  }

  public Function<String, ?> getArgumentSupplier() {
    return argumentSupplier;
  }

  @SuppressWarnings("ConstantConditions")
  public void run() {
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_RESET = "\u001B[0m";

    try {
      Day<?> day = dayClass.getConstructor().newInstance();

      System.out.println(
          String.format(
              ANSI_YELLOW + "\n--- Running %s ---\n" + ANSI_RESET, day.getClass().getSimpleName()));

      final boolean runExample1 = true;
      final boolean runPart1 = true;
      final boolean runExample2 = true;
      final boolean runPart2 = true;

      long time;

      if (runExample1) {
        System.out.println(ANSI_RED + "Example 1" + ANSI_RESET);
        time = System.currentTimeMillis();
        day.runExample1();
        System.out.println(
            String.format(
                ANSI_RED + "Example 1 took: %s ms\n" + ANSI_RESET,
                (System.currentTimeMillis() - time)));
      }

      if (runPart1) {
        System.out.println(ANSI_RED + "Part 1" + ANSI_RESET);
        time = System.currentTimeMillis();
        day.runPart1();
        System.out.println(
            String.format(
                ANSI_RED + "Part 1 took: %s ms\n" + ANSI_RESET,
                (System.currentTimeMillis() - time)));
      }

      if (runExample2) {
        System.out.println(ANSI_RED + "Example 2" + ANSI_RESET);
        time = System.currentTimeMillis();
        day.runExample2();
        System.out.println(
            String.format(
                ANSI_RED + "Example 2 took: %s ms\n" + ANSI_RESET,
                (System.currentTimeMillis() - time)));
      }

      if (runPart2) {
        System.out.println(ANSI_RED + "Part 2" + ANSI_RESET);
        time = System.currentTimeMillis();
        day.runPart2();
        System.out.println(
            String.format(
                ANSI_RED + "Part 2 took: %s ms\n" + ANSI_RESET,
                (System.currentTimeMillis() - time)));
      }

    } catch (NoSuchMethodException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
