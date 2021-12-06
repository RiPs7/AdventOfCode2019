package com.rips7.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends Day<List<String>> {

  public Day5() {
    super(DaysEnum.DAY_5);
  }

  @Override
  public void part1(final List<String> args) {
    final String intCodeInput = args.get(0);
    final Integer[] intCode =
        Arrays.stream(intCodeInput.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    final int input = 1;
    final List<Integer> outputs = executeProgram(intCode, input);
    if (outputs.isEmpty()) {
      return;
    }
    System.out.println(
        String.format(
            "After providing %s to the only input instruction and passing all the tests, the produced diagnostic code is %s.",
            input, outputs.get(outputs.size() - 1)));
  }

  @Override
  public void part2(final List<String> args) {
    final String intCodeInput = args.get(0);
    final Integer[] intCode =
        Arrays.stream(intCodeInput.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    final int input = 5;
    final List<Integer> outputs = executeProgram(intCode, input);
    if (outputs.isEmpty()) {
      return;
    }
    System.out.println(
        String.format(
            "After providing %s to the only input instruction and passing all the tests, the produced diagnostic code is %s.",
            input, outputs.get(outputs.size() - 1)));
  }

  private List<Integer> executeProgram(final Integer[] intCode, final int input) {
    final List<Integer> outputs = new ArrayList<>();
    int counter = 0;
    while (counter < intCode.length) {
      int instruction = intCode[counter];
      boolean param1ImmediateMode = instruction > 100 && (instruction / 100) % 10 == 1;
      boolean param2ImmediateMode = instruction > 1000 && (instruction / 1000) % 10 == 1;
      boolean param3ImmediateMode = instruction > 10000 && (instruction / 10000) % 10 == 1;
      instruction = instruction > 100 ? (instruction % 100) : instruction;
      int param1 = -1;
      int param2 = -1;
      int param3 = -1;
      try {
        param1 = param1ImmediateMode ? counter + 1 : intCode[counter + 1];
        param2 = param2ImmediateMode ? counter + 2 : intCode[counter + 2];
        param3 = param3ImmediateMode ? counter + 3 : intCode[counter + 3];
      } catch (final Exception e) {
        // suppress the exception
      }
      switch (instruction) {
        case 1: //addition
          intCode[param3] = intCode[param1] + intCode[param2];
          counter += 4;
          break;
        case 2: //multiplication
          intCode[param3] = intCode[param1] * intCode[param2];
          counter += 4;
          break;
        case 3: //store input
          intCode[param1] = input;
          counter += 2;
          break;
        case 4: //output data
          final int output = intCode[param1];
          outputs.add(output);
          counter += 2;
          break;
        case 5: //jump-if-true
          if (intCode[param1] != 0) {
            counter = intCode[param2];
          } else {
            counter += 3;
          }
          break;
        case 6: //jump-if-false
          if (intCode[param1] == 0) {
            counter = intCode[param2];
          } else {
            counter += 3;
          }
          break;
        case 7: //less than
          if (intCode[param1] < intCode[param2]) {
            intCode[param3] = 1;
          } else {
            intCode[param3] = 0;
          }
          counter += 4;
          break;
        case 8: //equals
          if (intCode[param1].equals(intCode[param2])) {
            intCode[param3] = 1;
          } else {
            intCode[param3] = 0;
          }
          counter += 4;
          break;
        case 99: //halt
          counter = intCode.length;
          break;
        default:
          throw new RuntimeException(String.format("Instruction [%s] not recognized", instruction));
      }
    }
    return outputs;
  }
}
