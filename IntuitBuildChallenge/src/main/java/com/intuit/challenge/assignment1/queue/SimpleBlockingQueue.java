package com.intuit.challenge.assignment1.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A custom implementation of a blocking queue using wait/notify mechanism.
 * This satisfies the "Wait/Notify mechanism" and "Thread synchronization" testing objectives.
 *
 * @param <T> The type of elements held in this queue
 */
public class SimpleBlockingQueue<T> {

  private final Queue<T> queue;
  private final int capacity;

  public SimpleBlockingQueue(int capacity) {
    this.queue = new LinkedList<>();
    this.capacity = capacity;
  }

  //Puts an item into the queue. Blocks if the queue is full.
  public synchronized void put(T item) throws InterruptedException {
    while (queue.size() == capacity) {
      // Queue is full, wait for consumer to consume
      System.out.println("Queue is full. Producer is waiting...");
      wait();
    }

    queue.add(item);
    System.out.println("Produced: " + item);

    // Notify consumer that data is available
    notifyAll();
  }

  //Takes an item from the queue. Blocks if the queue is empty.
  public synchronized T take() throws InterruptedException {
    while (queue.isEmpty()) {
      // Queue is empty, wait for producer to produce
      System.out.println("Queue is empty. Consumer is waiting...");
      wait();
    }

    T item = queue.remove();
    System.out.println("Consumed: " + item);

    // Notify producer that space is available
    notifyAll();
    return item;
  }

  public synchronized int size() {
    return queue.size();
  }
}
