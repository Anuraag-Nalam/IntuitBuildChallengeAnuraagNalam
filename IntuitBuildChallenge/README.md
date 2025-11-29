# Intuit Build Challenge - Assignment 1: Producer-Consumer Simulation

## Overview

This project satisfies **Assignment 1** of the Intuit Build Challenge. It implements a robust, thread-safe **Producer-Consumer** pattern in Java without using high-level concurrency libraries (like `java.util.concurrent.BlockingQueue`). Instead, it demonstrates core competency in **Thread Synchronization** and the **Wait/Notify mechanism** by implementing a custom blocking queue from scratch.

While the assignment describes a basic simulation, this solution is architected to scale, supporting **multiple producers** and **multiple consumers** concurrently, managed by dedicated Manager classes.

## Deliverables & Implementation Map

The following table maps the specific **Testing Objectives** and **Deliverables** from the assignment instructions to their concrete implementation in this project.

| Requirement / Objective          | Implementation Location                                                                                                                                                  | Description                                                                                            |
| :------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------- |
| **Thread Synchronization**       | `SimpleBlockingQueue.java`                                                                                                                                               | Uses `synchronized` methods to ensure thread safety when accessing the shared queue.                   |
| **Wait/Notify Mechanism**        | `SimpleBlockingQueue.java`                                                                                                                                               | Implements `wait()` when the queue is full/empty and `notifyAll()` to wake up waiting threads.         |
| **Blocking Queues**              | `SimpleBlockingQueue.java`                                                                                                                                               | A custom implementation of a blocking queue that handles flow control between producers and consumers. |
| **Concurrent Programming**       | `SimulationManager.java`                                                                                                                                                 | Orchestrates the concurrent execution of multiple Producer and Consumer threads.                       |
| **Multiple Producers/Consumers** | `ProducerManager.java` <br> `ConsumerManager.java`                                                                                                                       | Manages thread pools and lifecycles for scalable concurrency (beyond just 1 producer/1 consumer).      |
| **Unit Tests**                   | `SimpleBlockingQueueTest.java`<br>`ProducerManagerTest.java`<br>`ConsumerManagerTest.java`<br>`SimulationManagerTest.java`<br>`ProducerTest.java`<br>`ConsumerTest.java` | Comprehensive unit tests covering blocking behavior, ordering, and data integrity.                     |
| **Integration Tests**            | `ProducerConsumerIntegrationTest.java`                                                                                                                                   | End-to-end tests verifying the system under different loads (High Volume, Bottlenecks).                |

## Key Features

- **Custom Blocking Queue**: Built from the ground up to demonstrate understanding of low-level threading concepts.
- **Scalable Design**: Unlike a simple 1-to-1 example, this project uses `ProducerManager` and `ConsumerManager` to handle $N$ producers and $M$ consumers.
- **Graceful Shutdown**: Implements a Poison Pill strategy. When producers finish, special marker objects are sent to ensure consumers process all remaining data before terminating cleanly.
- **Configurable Scenarios**:
  - **Main App**: Runs with default settings (2 Producers, 2 Consumers). It can be configured accordingly.
  - **Integration Tests**: Runs specific scenarios defined in `src/test/resources/test-config.properties` (e.g., High Volume: 10 Producers/10 Consumers).

## Setup Instructions

### Prerequisites

- **Java JDK 8+**
- **JUnit 4.13.2** (JARs are provided in the `lib/` folder)

### 1. Compile the Project

Open a terminal in the project root (`IntuitBuildChallenge`) and run:

```powershell
javac -d bin -cp "lib/*" src/main/java/com/intuit/challenge/assignment1/config/*.java src/main/java/com/intuit/challenge/assignment1/queue/*.java src/main/java/com/intuit/challenge/assignment1/workers/*.java src/main/java/com/intuit/challenge/assignment1/managers/*.java src/main/java/com/intuit/challenge/assignment1/simulation/*.java src/main/java/com/intuit/challenge/assignment1/*.java
```

### 2. Run the Application

This executes the main simulation with the default configuration.

```powershell
java -cp "bin;src/main/resources" com.intuit.challenge.assignment1.Main
```

### 3. Run Tests

To verify the "Testing Objectives" (Unit Tests) and "Deliverables" (Integration Tests):

**Compile Tests:**

```powershell
javac -d bin -cp "lib/*;bin" src/test/java/com/intuit/challenge/assignment1/config/*.java src/test/java/com/intuit/challenge/assignment1/integration/*.java src/test/java/com/intuit/challenge/assignment1/queue/*.java src/test/java/com/intuit/challenge/assignment1/managers/*.java src/test/java/com/intuit/challenge/assignment1/simulation/*.java src/test/java/com/intuit/challenge/assignment1/workers/*.java
```

**Run Integration Tests (Multiple Scenarios):**

```powershell
java -cp "bin;lib/*;src/test/resources" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.integration.ProducerConsumerIntegrationTest
```

**Run Unit Tests:**

```powershell
# Queue Tests
java -cp "bin;lib/*" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.queue.SimpleBlockingQueueTest

# Manager Tests
java -cp "bin;lib/*" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.managers.ProducerManagerTest
java -cp "bin;lib/*" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.managers.ConsumerManagerTest

# Simulation Tests
java -cp "bin;lib/*" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.simulation.SimulationManagerTest

# Worker Tests
java -cp "bin;lib/*" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.workers.ProducerTest
java -cp "bin;lib/*" org.junit.runner.JUnitCore com.intuit.challenge.assignment1.workers.ConsumerTest
```

## Sample Output (Result of running the main application)

Below is an example output demonstrating the **Wait/Notify** mechanism in action. Note the "Queue is full" and "Queue is empty" messages, proving the blocking behavior.

```text
Starting simulation with 2 producers and 2 consumers...
Queue is empty. Consumer is waiting...   <-- Consumer waits (notify)
Produced: 1
Consumed: 1                              <-- Consumer wakes up
Produced: 2
Consumed: 2
...
Produced: 5
Queue is full. Producer is waiting...    <-- Producer waits (wait)
Consumed: 3
Produced: 6                              <-- Producer wakes up
...
All producers finished.
Consumer-1 received stop signal.
Consumer-2 received stop signal.
All consumers finished.
Simulation Finished. Consumed items: [1, 2, ..., 100]
Time: 0.234s
```
