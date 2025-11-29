package com.intuit.challenge.assignment1.workers;

import static org.junit.Assert.*;

import com.intuit.challenge.assignment1.queue.SimpleBlockingQueue;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class ProducerTest {

  @Test
  public void testProducerRun() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
    List<Integer> data = Arrays.asList(1, 2, 3);
    Producer producer = new Producer(queue, data);

    Thread t = new Thread(producer);
    t.start();
    t.join();

    assertEquals(3, queue.size());
    assertEquals(Integer.valueOf(1), queue.take());
    assertEquals(Integer.valueOf(2), queue.take());
    assertEquals(Integer.valueOf(3), queue.take());
  }
}
