package com.intuit.challenge.assignment1.managers;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import com.intuit.challenge.assignment1.workers.Consumer;
import java.util.ArrayList;
import java.util.List;

public class ConsumerManager {

  private final SimpleBlockingQueue<Integer> sharedQueue;
  private final List<Thread> consumerThreads;
  private final int workerCount;

  public ConsumerManager(
    SimpleBlockingQueue<Integer> sharedQueue,
    List<Integer> destinationList,
    int workerCount
  ) {
    this.sharedQueue = sharedQueue;
    this.workerCount = workerCount;
    this.consumerThreads = new ArrayList<>();
    initializeConsumers(destinationList, workerCount);
  }

  private void initializeConsumers(
    List<Integer> destinationList,
    int workerCount
  ) {
    for (int i = 0; i < workerCount; i++) {
      Consumer consumer = new Consumer(sharedQueue, destinationList);
      Thread thread = new Thread(consumer, "Consumer-" + (i + 1));
      consumerThreads.add(thread);
    }
  }

  public void start() {
    for (Thread t : consumerThreads) {
      t.start();
    }
  }

  public void stopConsumers() throws InterruptedException {
    // Send one poison pill per consumer
    for (int i = 0; i < workerCount; i++) {
      sharedQueue.put(Consumer.POISON_PILL);
    }
  }

  public void waitForCompletion() throws InterruptedException {
    for (Thread t : consumerThreads) {
      t.join();
    }
  }

  public int getThreadCount() {
    return consumerThreads.size();
  }
}
