package com.intuit.challenge.assignment1.workers;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import java.util.List;

/**
 * Producer thread that reads from a source and places items into the shared queue.
 */
public class Producer implements Runnable {

  private final SimpleBlockingQueue<Integer> sharedQueue;
  private final List<Integer> sourceData;

  public Producer(
    SimpleBlockingQueue<Integer> sharedQueue,
    List<Integer> sourceData
  ) {
    this.sharedQueue = sharedQueue;
    this.sourceData = sourceData;
  }

  @Override
  public void run() {
    try {
      for (Integer item : sourceData) {
        sharedQueue.put(item);
        // Simulate some processing time
        Thread.sleep(100);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Producer interrupted");
    }
  }
}
