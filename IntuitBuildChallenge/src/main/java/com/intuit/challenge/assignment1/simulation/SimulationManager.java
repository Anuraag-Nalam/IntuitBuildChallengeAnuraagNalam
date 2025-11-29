package com.intuit.challenge.assignment1.simulation;

import com.intuit.challenge.assignment1.managers.ConsumerManager;
import com.intuit.challenge.assignment1.managers.ProducerManager;
import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationManager {

  public List<Integer> runSimulation(
    List<Integer> sourceData,
    int producerCount,
    int consumerCount,
    int queueCapacity
  ) {
    System.out.println("Starting Simulation...");

    SimpleBlockingQueue<Integer> sharedQueue = new SimpleBlockingQueue<>(
      queueCapacity
    );
    List<Integer> destinationData = Collections.synchronizedList(
      new ArrayList<>()
    );

    ProducerManager producerManager = new ProducerManager(
      sharedQueue,
      sourceData,
      producerCount
    );
    ConsumerManager consumerManager = new ConsumerManager(
      sharedQueue,
      destinationData,
      consumerCount
    );

    consumerManager.start();
    producerManager.start();

    try {
      producerManager.waitForCompletion();
      System.out.println("All producers finished.");

      consumerManager.stopConsumers();
      consumerManager.waitForCompletion();
      System.out.println("All consumers finished.");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    }

    destinationData.sort(Integer::compareTo);
    System.out.println(
      "Simulation Finished. Consumed items: " + destinationData
    );
    return destinationData;
  }
}
