package com.intuit.challenge.assignment1.managers;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import com.intuit.challenge.assignment1.workers.Producer;
import java.util.ArrayList;
import java.util.List;

public class ProducerManager {

  private final SimpleBlockingQueue<Integer> sharedQueue;
  private final List<Thread> producerThreads;

  public ProducerManager(
    SimpleBlockingQueue<Integer> sharedQueue,
    List<Integer> allData,
    int workerCount
  ) {
    this.sharedQueue = sharedQueue;
    this.producerThreads = new ArrayList<>();
    initializeProducers(allData, workerCount);
  }

  private void initializeProducers(List<Integer> allData, int workerCount) {
    if (workerCount <= 0) return;

    // Calculate chunk size
    int totalSize = allData.size();
    int chunkSize = (int) Math.ceil((double) totalSize / workerCount);

    for (int i = 0; i < workerCount; i++) {
      int start = i * chunkSize;
      // If start is beyond data size, no more data for remaining workers
      if (start >= totalSize) break;

      int end = Math.min(start + chunkSize, totalSize);

      List<Integer> subList = new ArrayList<>(allData.subList(start, end));
      Producer producer = new Producer(sharedQueue, subList);
      Thread thread = new Thread(producer, "Producer-" + (i + 1));
      producerThreads.add(thread);
    }
  }

  public void start() {
    for (Thread t : producerThreads) {
      t.start();
    }
  }

  public void waitForCompletion() throws InterruptedException {
    for (Thread t : producerThreads) {
      t.join();
    }
  }

  public int getThreadCount() {
    return producerThreads.size();
  }
}
