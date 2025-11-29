package com.intuit.challenge.assignment1.integration;

import static org.junit.Assert.*;

import com.intuit.challenge.assignment1.config.TestConfig;
import com.intuit.challenge.assignment1.simulation.SimulationManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

public class ProducerConsumerIntegrationTest {

  private TestConfig testConfig;

  @Before
  public void setUp() {
    testConfig = new TestConfig();
  }

  @Test
  public void testHighVolumeDataProcessing() {
    int itemCount = testConfig.getIntProperty("high.volume.item.count", 1000);
    int producerCount = testConfig.getIntProperty(
      "high.volume.producer.count",
      10
    );
    int consumerCount = testConfig.getIntProperty(
      "high.volume.consumer.count",
      10
    );
    int queueCapacity = testConfig.getIntProperty(
      "high.volume.queue.capacity",
      50
    );

    List<Integer> inputData = IntStream.rangeClosed(1, itemCount)
      .boxed()
      .collect(Collectors.toList());

    SimulationManager simulation = new SimulationManager();
    List<Integer> result = simulation.runSimulation(
      inputData,
      producerCount,
      consumerCount,
      queueCapacity
    );

    assertEquals("Should consume all items", itemCount, result.size());
    assertEquals("Data integrity check", inputData, result);
  }

  @Test
  public void testProducerBottleneck() {
    int itemCount = testConfig.getIntProperty(
      "prod.bottleneck.item.count",
      100
    );
    int producerCount = testConfig.getIntProperty(
      "prod.bottleneck.producer.count",
      1
    );
    int consumerCount = testConfig.getIntProperty(
      "prod.bottleneck.consumer.count",
      10
    );
    int queueCapacity = testConfig.getIntProperty(
      "prod.bottleneck.queue.capacity",
      5
    );

    List<Integer> inputData = IntStream.rangeClosed(1, itemCount)
      .boxed()
      .collect(Collectors.toList());

    SimulationManager simulation = new SimulationManager();
    List<Integer> result = simulation.runSimulation(
      inputData,
      producerCount,
      consumerCount,
      queueCapacity
    );

    assertEquals(itemCount, result.size());
    assertEquals(inputData, result);
  }

  @Test
  public void testConsumerBottleneck() {
    int itemCount = testConfig.getIntProperty(
      "cons.bottleneck.item.count",
      100
    );
    int producerCount = testConfig.getIntProperty(
      "cons.bottleneck.producer.count",
      10
    );
    int consumerCount = testConfig.getIntProperty(
      "cons.bottleneck.consumer.count",
      1
    );
    int queueCapacity = testConfig.getIntProperty(
      "cons.bottleneck.queue.capacity",
      5
    );

    List<Integer> inputData = IntStream.rangeClosed(1, itemCount)
      .boxed()
      .collect(Collectors.toList());

    SimulationManager simulation = new SimulationManager();
    List<Integer> result = simulation.runSimulation(
      inputData,
      producerCount,
      consumerCount,
      queueCapacity
    );

    assertEquals(itemCount, result.size());
    assertEquals(inputData, result);
  }

  @Test
  public void testEmptyInput() {
    List<Integer> inputData = new ArrayList<>();
    SimulationManager simulation = new SimulationManager();
    List<Integer> result = simulation.runSimulation(inputData, 2, 2, 5);

    assertTrue(result.isEmpty());
  }
}
