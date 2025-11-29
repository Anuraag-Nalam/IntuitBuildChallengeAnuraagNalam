package com.intuit.challenge.assignment1;

import com.intuit.challenge.assignment1.config.AppConfig;
import com.intuit.challenge.assignment1.simulation.SimulationManager;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    // Load configuration
    AppConfig config = new AppConfig();
    int producerCount = config.getProducerCount();
    int consumerCount = config.getConsumerCount();
    int queueCapacity = config.getQueueCapacity();

    System.out.println("Configuration Loaded:");
    System.out.println("Producers: " + producerCount);
    System.out.println("Consumers: " + consumerCount);
    System.out.println("Queue Capacity: " + queueCapacity);

    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    SimulationManager simulation = new SimulationManager();
    simulation.runSimulation(data, producerCount, consumerCount, queueCapacity);
  }
}
