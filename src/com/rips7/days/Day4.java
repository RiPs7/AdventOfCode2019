package com.rips7.days;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 extends Day<List<String>> {

  public Day4() {
    super(DaysEnum.DAY_4);
  }

  @Override
  public void part1(final List<String> args) {
    if (args.size() == 0) {
      System.out.println("Skipping... No input was given.");
      return;
    }
    final String[] range = args.get(0).split("-");
    final int min = Integer.parseInt(range[0]);
    final int max = Integer.parseInt(range[1]);

    final List<Integer> candidates =
        IntStream.rangeClosed(min, max)
            .boxed()
            .filter(this::twoAdjacentDigitsSame)
            .filter(this::nonIncreasingDigits)
            .collect(Collectors.toList());

    System.out.println(
        String.format(
            "There are %s different passwords that meet the criteria.", candidates.size()));
  }

  @Override
  public void part2(final List<String> args) {
    if (args.size() == 0) {
      System.out.println("Skipping... No input was given.");
      return;
    }
    final String[] range = args.get(0).split("-");
    final int min = Integer.parseInt(range[0]);
    final int max = Integer.parseInt(range[1]);

    final List<Integer> candidates =
        IntStream.rangeClosed(min, max)
            .boxed()
            .filter(this::twoAdjacentDigitsSame)
            .filter(this::onlyTwoAdjacentDigitsSame)
            .filter(this::nonIncreasingDigits)
            .collect(Collectors.toList());

    System.out.println(
        String.format(
            "There are %s different passwords that meet the criteria.", candidates.size()));
  }

  private boolean twoAdjacentDigitsSame(int num) {
    char[] digits = String.valueOf(num).toCharArray();
    for (int i = 0; i < digits.length - 1; i++) {
      if (digits[i] == digits[i + 1]) {
        return true;
      }
    }
    return false;
  }

  private boolean nonIncreasingDigits(int num) {
    char[] digits = String.valueOf(num).toCharArray();
    for (int i = 0; i < digits.length - 1; i++) {
      if (digits[i] > digits[i + 1]) {
        return false;
      }
    }
    return true;
  }

  private boolean onlyTwoAdjacentDigitsSame(int num) {
    char[] digits = String.valueOf(num).toCharArray();
    for (int i = 0; i < digits.length - 2; i++) {
      if (digits[i] == digits[i + 1]
          && digits[i] == digits[i + 2]) { // there is a group larger than two equal digits
        if (digits[0] == digits[1] && digits[1] != digits[2]) { // check first two digits
          return true;
        } else if (digits[digits.length - 1] == digits[digits.length - 2]
            && digits[digits.length - 2] != digits[digits.length - 3]) { // check last two digits
          return true;
        } else {
          for (int j = 1; j < digits.length - 2; j++) {
            // check intermediate two digits bounded by different digits
            if (digits[j] == digits[j + 1]
                && digits[j] != digits[j - 1]
                && digits[j + 1] != digits[j + 2]) {
              return true;
            }
          }
        }
        return false;
      }
    }
    return true;
  }
}
