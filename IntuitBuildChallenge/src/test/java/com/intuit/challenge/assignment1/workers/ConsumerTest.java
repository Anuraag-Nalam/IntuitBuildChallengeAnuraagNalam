package com.intuit.challenge.assignment1.workers;

import static org.junit.Assert.*;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class ConsumerTest {

  @Test
  public void testConsumerRun() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
    List<Integer> destination = Collections.synchronizedList(new ArrayList<>());
    Consumer consumer = new Consumer(queue, destination);

    queue.put(10);
    queue.put(20);
    queue.put(Consumer.POISON_PILL);

    Thread t = new Thread(consumer);
    t.start();
    t.join();

    assertEquals(2, destination.size());
    assertTrue(destination.contains(10));
    assertTrue(destination.contains(20));
  }
}
