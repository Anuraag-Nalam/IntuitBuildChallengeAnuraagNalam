package com.intuit.challenge.assignment1.managers;

import static org.junit.Assert.*;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import com.intuit.challenge.assignment1.workers.Consumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class ConsumerManagerTest {

  @Test
  public void testConsumerManagerLifecycle() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
    List<Integer> destination = Collections.synchronizedList(new ArrayList<>());

    ConsumerManager manager = new ConsumerManager(queue, destination, 2);
    assertEquals(2, manager.getThreadCount());

    manager.start();

    // Produce some data
    queue.put(1);
    queue.put(2);
    queue.put(3);

    // Stop consumers
    manager.stopConsumers();
    manager.waitForCompletion();

    assertEquals(3, destination.size());
    // Queue should be empty (poison pills consumed)
    assertEquals(0, queue.size());
  }
}
