package com.rips7.days;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day6 extends Day<List<String>> {

  public Day6() {
    super(DaysEnum.DAY_6);
  }

  @Override
  public void part1(final List<String> args) {
    final List<String[]> orbits =
        args.stream().map(line -> line.split("\\)")).collect(Collectors.toList());

    // Direct orbits are stored in a map, that defines Map#key orbits Map#value
    final Map<String, String> directOrbits =
        orbits.stream().collect(Collectors.toMap(orbit -> orbit[1], orbit -> orbit[0]));

    int totalOrbitCount =
        directOrbits.keySet().stream()
            .map(planet -> countOrbits(directOrbits, planet, 0))
            .mapToInt(Integer::intValue)
            .sum();

    System.out.println(String.format("There are %s direct and indirect orbits.", totalOrbitCount));
  }

  @Override
  public void part2(final List<String> args) {
    final List<String[]> orbits =
        args.stream().map(line -> line.split("\\)")).collect(Collectors.toList());

    // Direct orbits are stored in a map, that defines Map#key orbits Map#value
    final Map<String, String> directOrbits =
        orbits.stream().collect(Collectors.toMap(orbit -> orbit[1], orbit -> orbit[0]));

    final int exactOrbitTransfers = countOrbitalTransfers(directOrbits, "YOU", "SAN");

    System.out.println(
        String.format(
            "The minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting is: %s",
            exactOrbitTransfers - 2));
  }

  private int countOrbits(
      final Map<String, String> orbits, final String currentPlanet, final int currentCount) {
    return "COM".equals(currentPlanet)
        ? currentCount
        : countOrbits(orbits, orbits.get(currentPlanet), currentCount + 1);
  }

  @SuppressWarnings("SameParameterValue")
  private int countOrbitalTransfers(
      final Map<String, String> orbits, final String startPlanet, final String endPlanet) {
    // Apply DFS
    final Stack<OrbitalTransfer> frontier = new Stack<>();
    frontier.add(new OrbitalTransfer(startPlanet, orbits.get(startPlanet), 0));
    final Set<OrbitalTransfer> closedSet = new HashSet<>();
    while (!frontier.isEmpty()) {
      final OrbitalTransfer current = frontier.pop();
      if (!closedSet.add(current)) {
        continue;
      }
      if (Optional.ofNullable(current.child).orElse("-").equals(endPlanet)
          || Optional.ofNullable(current.parent).orElse("-").equals(endPlanet)) {
        break;
      }
      frontier.add(
          new OrbitalTransfer(current.child, orbits.get(current.child), current.transferCount + 1));
      frontier.addAll(
          orbits.entrySet().stream()
              .filter(entry -> entry.getValue().equals(current.parent))
              .map(
                  entry ->
                      new OrbitalTransfer(
                          entry.getKey(), entry.getValue(), current.transferCount + 1))
              .collect(Collectors.toList()));
    }

    return closedSet.stream()
        .filter(
            orbitalTransfer ->
                orbitalTransfer.child.equals(endPlanet) || orbitalTransfer.parent.equals(endPlanet))
        .findFirst()
        .orElseThrow()
        .transferCount;
  }

  private static final class OrbitalTransfer {
    final String parent;
    final String child;
    final int transferCount;

    public OrbitalTransfer(final String parent, final String child, final int transferCount) {
      this.parent = parent;
      this.child = child;
      this.transferCount = transferCount;
    }

    @Override
    public int hashCode() {
      return Objects.hash(child, parent);
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof OrbitalTransfer)) {
        return false;
      }

      final OrbitalTransfer other = (OrbitalTransfer) obj;

      return Objects.equals(child, other.child) && Objects.equals(parent, other.parent);
    }

    @Override
    public String toString() {
      return String.format("%s(%s - %s", parent, child, transferCount);
    }
  }
}
