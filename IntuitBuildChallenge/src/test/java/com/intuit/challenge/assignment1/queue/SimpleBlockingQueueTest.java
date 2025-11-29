package com.intuit.challenge.assignment1.queue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class SimpleBlockingQueueTest {

  @Test
  public void testPutAndTake() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
    queue.put(1);
    assertEquals(1, queue.size());

    Integer item = queue.take();
    assertEquals(Integer.valueOf(1), item);
    assertEquals(0, queue.size());
  }

  @Test
  public void testBlockingOnFull() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
    List<String> events = Collections.synchronizedList(new ArrayList<>());

    Thread producer = new Thread(() -> {
      try {
        queue.put(1);
        events.add("Produced 1");
        queue.put(2); // Should block
        events.add("Produced 2");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread consumer = new Thread(() -> {
      try {
        Thread.sleep(200);
        events.add("Consuming");
        queue.take();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    producer.start();
    consumer.start();
    producer.join(1000);
    consumer.join(1000);

    assertTrue(events.contains("Produced 1"));
    assertTrue(events.contains("Consuming"));
    assertTrue(events.contains("Produced 2"));

    int consumeIndex = events.indexOf("Consuming");
    int produce2Index = events.indexOf("Produced 2");
    assertTrue(
      "Producer should wait for consumer",
      produce2Index > consumeIndex
    );
  }

  @Test
  public void testBlockingOnEmpty() throws InterruptedException {
    SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
    List<String> events = Collections.synchronizedList(new ArrayList<>());

    Thread consumer = new Thread(() -> {
      try {
        events.add("Trying to consume");
        queue.take(); // Should block
        events.add("Consumed");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    Thread producer = new Thread(() -> {
      try {
        Thread.sleep(200);
        events.add("Producing");
        queue.put(1);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    });

    consumer.start();
    producer.start();
    consumer.join(1000);
    producer.join(1000);

    assertTrue(events.contains("Trying to consume"));
    assertTrue(events.contains("Producing"));
    assertTrue(events.contains("Consumed"));

    int produceIndex = events.indexOf("Producing");
    int consumeIndex = events.indexOf("Consumed");
    assertTrue(
      "Consumer should wait for producer",
      consumeIndex > produceIndex
    );
  }
}
