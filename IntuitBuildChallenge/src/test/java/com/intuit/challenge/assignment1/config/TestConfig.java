package com.intuit.challenge.assignment1.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

  private final Properties properties;

  public TestConfig() {
    properties = new Properties();
    loadProperties();
  }

  private void loadProperties() {
    try (
      InputStream input = getClass()
        .getClassLoader()
        .getResourceAsStream("test-config.properties")
    ) {
      if (input == null) {
        System.out.println("Sorry, unable to find test-config.properties");
        return;
      }
      properties.load(input);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public int getIntProperty(String key, int defaultValue) {
    String value = properties.getProperty(key);
    if (value == null) {
      return defaultValue;
    }
    return Integer.parseInt(value);
  }
}
