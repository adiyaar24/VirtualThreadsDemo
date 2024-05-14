# VirtualThreadsDemo

## Description

VirtualThreadsDemo is a basic demonstration to showcase working with Virtual Threads in Java. This project provides simple examples to understand the usage and functionality of Virtual Threads in Java applications.

## What is a Thread?

Threads are fundamental units of execution in Java, enabling simultaneous multitasking and enhancing application performance. In simple terms, a thread represents a sequence of instructions that can execute independently within a program. This concurrency mechanism allows developers to execute multiple tasks concurrently, improving overall execution time and efficiency.

## Virtual Threads: A Lightweight Concurrency Solution

### Overview

Virtual Threads represent a groundbreaking feature introduced in Java 19, available in preview mode, and fully supported for production use from Java 21 LTS version onwards.

### Why were Virtual Threads added to JDK?

The addition of Virtual Threads to the Java platform addresses the need for a more lightweight and efficient thread type. By reducing the resource overhead associated with traditional threads, Virtual Threads simplify the development, maintenance, and debugging of high-throughput concurrent applications.

### How do Virtual Threads work under the hood?

In Java's virtual threading model, Virtual Threads are instances of the Thread class, similar to OS threads. However, unlike traditional threads, Virtual Threads are not directly tied to specific OS threads. Instead, they leverage the concept of carrier threads, a small number of OS threads utilized to execute multiple Virtual Threads concurrently.

- **Carrier Thread:** Virtual Threads are associated with carrier threads, allowing the Java Virtual Machine (JVM) to manage a large number of Virtual Threads efficiently while minimizing overhead.

- **Handling Blocking Operations:** During blocking I/O operations, traditional OS threads incur context switches, leading to potential performance bottlenecks. However, with Virtual Threads managed by the JVM, blocking operations are handled efficiently without blocking underlying OS threads. This is achieved by storing the state of Virtual Threads in the heap, enabling seamless execution on the same Java platform (carrier) thread.

By leveraging Virtual Threads, developers can achieve unprecedented scalability and performance in concurrent applications, without the complexities associated with traditional threading models.

## Requirements

- **Java:** Version 21 or higher.
- **Maven:** Version 3.9.6 or higher is preferable.

## License

This project is licensed under the MIT License.

## Author

VirtualThreadsDemo was created by Diego Pérez V.

---
