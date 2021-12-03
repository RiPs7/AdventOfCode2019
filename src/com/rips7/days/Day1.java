package com.rips7.days;

import java.util.List;

public class Day1 extends Day<List<Integer>> {

  public Day1() {
    super(DaysEnum.DAY_1);
  }

  @Override
  public void part1(final List<Integer> args) {
    final int totalFuelRequirement = args.stream().map(mass -> mass / 3 - 2).reduce(Integer::sum).orElse(0);
    System.out.println(String.format("The total fuel requirement is: %s", totalFuelRequirement));
  }

  @Override
  public void part2(final List<Integer> args) {
    final int totalFuelRequirement = args.stream().map(mass -> {
      int totalFuel = 0;
      int currentFuel = mass / 3 - 2;
      while (currentFuel > 0) {
        totalFuel += currentFuel;
        currentFuel = currentFuel / 3 - 2;
      }
      return totalFuel;
    }).reduce(Integer::sum).orElse(0);
    System.out.println(String.format("The total fuel requirement is: %s", totalFuelRequirement));
  }
}
