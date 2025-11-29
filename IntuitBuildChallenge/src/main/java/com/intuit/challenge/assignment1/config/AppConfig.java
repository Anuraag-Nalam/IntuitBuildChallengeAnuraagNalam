package com.intuit.challenge.assignment1.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

  private final Properties properties;

  public AppConfig() {
    properties = new Properties();
    loadProperties();
  }

  private void loadProperties() {
    try (
      InputStream input = getClass()
        .getClassLoader()
        .getResourceAsStream("config.properties")
    ) {
      if (input == null) {
        System.out.println("Sorry, unable to find config.properties");
        return;
      }
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public int getProducerCount() {
    return Integer.parseInt(properties.getProperty("producer.count", "2"));
  }

  public int getConsumerCount() {
    return Integer.parseInt(properties.getProperty("consumer.count", "2"));
  }

  public int getQueueCapacity() {
    return Integer.parseInt(properties.getProperty("queue.capacity", "5"));
  }
}
