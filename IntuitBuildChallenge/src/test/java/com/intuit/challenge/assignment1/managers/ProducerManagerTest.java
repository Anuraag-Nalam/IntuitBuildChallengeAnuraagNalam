package com.intuit.challenge.assignment1.managers;

import static org.junit.Assert.*;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ProducerManagerTest {

  @Test
  public void testProducerManagerSplitsData() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6);

    // 2 workers, should get 3 items each
    ProducerManager manager = new ProducerManager(queue, data, 2);
    assertEquals(2, manager.getThreadCount());

    manager.start();
    manager.waitForCompletion();

    assertEquals(6, queue.size());
  }

  @Test
  public void testProducerManagerUnevenSplit() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);

    // 2 workers: one gets 3, one gets 2
    ProducerManager manager = new ProducerManager(queue, data, 2);
    assertEquals(2, manager.getThreadCount());

    manager.start();
    manager.waitForCompletion();

    assertEquals(5, queue.size());
  }
}
