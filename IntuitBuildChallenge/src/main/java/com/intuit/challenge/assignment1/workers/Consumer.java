package com.intuit.challenge.assignment1.workers;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import java.util.List;

/**
 * Consumer thread that reads from the shared queue and stores items in a destination container.
 */
public class Consumer implements Runnable {

  private final SimpleBlockingQueue<Integer> sharedQueue;
  private final List<Integer> sharedDestinationData;
  public static final Integer POISON_PILL = -1;

  public Consumer(
    SimpleBlockingQueue<Integer> sharedQueue,
    List<Integer> sharedDestinationData
  ) {
    this.sharedQueue = sharedQueue;
    this.sharedDestinationData = sharedDestinationData;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Integer item = sharedQueue.take();

        // Check for Poison Pill to stop consumption
        if (item.equals(POISON_PILL)) {
          System.out.println(
            Thread.currentThread().getName() + " received stop signal."
          );
          break;
        }

        synchronized (sharedDestinationData) {
          sharedDestinationData.add(item);
        }

        // Simulate processing time
        Thread.sleep(50);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("Consumer interrupted");
    }
  }
}
