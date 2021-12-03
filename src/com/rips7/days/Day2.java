package com.rips7.days;

import java.util.Arrays;
import java.util.List;

public class Day2 extends Day<List<String>> {

  public Day2() {
    super(DaysEnum.DAY_2);
  }

  @Override
  public void part1(final List<String> args) {
    final String intCodeInput = args.get(0);
    final Integer[] intCode =
        Arrays.stream(intCodeInput.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    if (intCode[1] != 9) { // This only happens for the example.
      intCode[1] = 12;
      intCode[2] = 2;
    }
    final int result = executeProgram(intCode);
    System.out.println(
        String.format("The value at position 0 after the program halts is: %s", result));
  }

  @Override
  public void part2(final List<String> args) {
    final String intCodeInput = args.get(0);
    final Integer[] intCode =
        Arrays.stream(intCodeInput.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    final int target = 19690720;
    int correctNoun = -1;
    int correctVerb = -1;
    for (int noun = 0; noun < 100; noun++) {
      for (int verb = 0; verb < 100; verb++) {
        try {
          final Integer[] intCodeCopy = Arrays.copyOf(intCode, intCode.length);
          intCodeCopy[1] = noun;
          intCodeCopy[2] = verb;
          final int result = executeProgram(intCodeCopy);
          if (result == target) {
            correctNoun = noun;
            correctVerb = verb;
            break;
          }
        } catch (final Exception e) {
          // suppress exception;
        }
      }
    }
    if (correctNoun == -1) {
      System.out.println("Not found for the given input");
      return;
    }
    System.out.println(
        String.format(
            "The noun and verb that produces %s are %s and %s. Value of 100 * noun + verb = %s",
            target, correctNoun, correctVerb, 100 * correctNoun + correctVerb));
  }

  private int executeProgram(final Integer[] intCode) {
    int counter = 0;
    while (counter < intCode.length) {
      switch (intCode[counter]) {
        case 1:
          intCode[intCode[counter + 3]] =
              intCode[intCode[counter + 1]] + intCode[intCode[counter + 2]];
          counter += 4;
          break;
        case 2:
          intCode[intCode[counter + 3]] =
              intCode[intCode[counter + 1]] * intCode[intCode[counter + 2]];
          counter += 4;
          break;
        case 99:
          counter = intCode.length;
          break;
        default:
          throw new RuntimeException("");
      }
    }
    return intCode[0];
  }
}
