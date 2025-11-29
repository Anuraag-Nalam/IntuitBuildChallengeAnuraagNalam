package com.intuit.challenge.assignment1.simulation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class SimulationManagerTest {

  @Test
  public void testFullSimulation() {
    SimulationManager sim = new SimulationManager();
    List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    List<Integer> result = sim.runSimulation(input, 2, 2, 5);

    assertEquals(input.size(), result.size());
    assertEquals(input, result);
  }

  @Test
  public void testSimulationWithMoreConsumersThanProducers() {
    SimulationManager sim = new SimulationManager();
    List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);

    List<Integer> result = sim.runSimulation(input, 1, 3, 5);

    assertEquals(input.size(), result.size());
    assertEquals(input, result);
  }
}
