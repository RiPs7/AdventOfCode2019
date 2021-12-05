package com.rips7.days;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day3 extends Day<List<String>> {

  public Day3() {
    super(DaysEnum.DAY_3);
  }

  @Override
  public void part1(final List<String> args) {
    final String[] wire1Directions = args.get(0).split(",");
    final String[] wire2Directions = args.get(1).split(",");

    final List<Point> wire1Path = createWirePath(wire1Directions);
    final Set<Point> wire1 = new HashSet<>(wire1Path);

    final List<Point> wire2Path = createWirePath(wire2Directions);
    final Set<Point> wire2 = new HashSet<>(wire2Path);

    final Set<Point> intersections = new HashSet<>(wire1);
    intersections.retainAll(wire2);

    final int closestPointManhattanDistance =
        intersections.stream()
            .map(pt -> Math.abs(pt.x) + Math.abs(pt.y))
            .filter(dist -> dist > 0)
            .min(Integer::compareTo)
            .orElse(0);

    System.out.println(
        String.format(
            "The Manhattan distance from the central port to the closest intersection is %s",
            closestPointManhattanDistance));
  }

  @Override
  public void part2(final List<String> args) {
    final String[] wire1Directions = args.get(0).split(",");
    final String[] wire2Directions = args.get(1).split(",");

    final List<Point> wire1Path = createWirePath(wire1Directions);
    final Set<Point> wire1 = new HashSet<>(wire1Path);

    final List<Point> wire2Path = createWirePath(wire2Directions);
    final Set<Point> wire2 = new HashSet<>(wire2Path);

    final Set<Point> intersections = new HashSet<>(wire1);
    intersections.retainAll(wire2);

    final int combinedStepsForWire1 =
        getCombinedStepsToFirstIntersection(wire1Path, wire2Path, intersections);
    final int combinedStepsForWire2 =
        getCombinedStepsToFirstIntersection(wire2Path, wire1Path, intersections);
    final int fewestCombinedSteps = Math.min(combinedStepsForWire1, combinedStepsForWire2);

    System.out.println(
        String.format(
            "The fewest combined steps the wires must take to reach an intersection %s",
            fewestCombinedSteps));
  }

  private List<Point> createWirePath(final String[] wireDirections) {
    final List<Point> wirePath = new ArrayList<>();
    Point currentPoint = new Point(0, 0);
    for (final String dirLen : wireDirections) {
      final char dir = dirLen.charAt(0);
      final int len = Integer.parseInt(dirLen.substring(1));
      for (int i = 0; i < len; i++) {
        switch (dir) {
          case 'R':
            currentPoint = new Point(currentPoint.x + 1, currentPoint.y);
            break;
          case 'U':
            currentPoint = new Point(currentPoint.x, currentPoint.y + 1);
            break;
          case 'L':
            currentPoint = new Point(currentPoint.x - 1, currentPoint.y);
            break;
          case 'D':
            currentPoint = new Point(currentPoint.x, currentPoint.y - 1);
            break;
        }
        wirePath.add(currentPoint);
      }
    }
    return wirePath;
  }

  public int getCombinedStepsToFirstIntersection(
      final List<Point> wireA, final List<Point> wireB, final Set<Point> intersections) {
    int fewestCombinedSteps = 0;
    Point firstIntersection = null;
    for (int i = 0; i < wireA.size(); i++) {
      final Point point = wireA.get(i);
      if (intersections.contains(point)) {
        fewestCombinedSteps =
            i + 1; // adds 1 to include the step from {0,0} to the beginning of <wire>.
        firstIntersection = point;
        break;
      }
    }

    assert firstIntersection != null;
    for (int i = 0; i < wireB.size(); i++) {
      final Point point = wireB.get(i);
      if (firstIntersection.equals(point)) {
        fewestCombinedSteps +=
            i + 1; // adds 1 to include the step from {0,0} to the beginning of <wire>.
        return fewestCombinedSteps;
      }
    }

    return -1;
  }

  public static final class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof Point)) {
        return false;
      }

      final Point other = (Point) obj;

      return Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y);
    }

    @Override
    public String toString() {
      return String.format("{%s,%s}", x, y);
    }
  }
}
