package com.piedpiper.virtualthreads;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VirtualThreads {

    private static final Logger LOGGER = Logger.getLogger(VirtualThreads.class.getName());

    public void processOrders(final List<Order> orders) {
        try (final var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            final var results = new ArrayList<Future<Optional<String>>>();

            orders.forEach(order -> {
                final Callable<Optional<String>> task = order::processOrder;
                final var taskResult = executorService.submit(task);
                results.add(taskResult);
            });

            results.forEach(this::processResult);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error processing orders. Exception: " + ex, ex);
        }
    }

    private void processResult(Future<Optional<String>> future) {
        try {
            var optOrderId = future.get();

            optOrderId.ifPresent(orderId -> LOGGER.info("The order with ID %s has been processed successfully.".formatted(orderId)));
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, "Thread interrupted while waiting for the result. | Exception: %s".formatted(ex), ex);
        } catch (ExecutionException ex) {
            LOGGER.log(Level.SEVERE, "Error processing order. Execution exception occurred. | Exception: %s".formatted(ex), ex);
        }
    }

    public int calculateThreadPoolSize(double waitTimeInSeconds, double computeTimeInSeconds, double utilization) {
        var numCPUs = getAvailableProcessors();
        return (int) (numCPUs * utilization * (1 + waitTimeInSeconds / computeTimeInSeconds));
    }

    private int getAvailableProcessors() {
        var osBean = ManagementFactory.getOperatingSystemMXBean();
        return osBean.getAvailableProcessors();
    }

    public void createVTWithThreadClass() {
        Runnable task = () -> System.out.println("My first Virtual Thread");
        Thread thread = Thread.ofVirtual().start(task);
    }

    public void createVTWithBuilderPattern() throws InterruptedException {
        Runnable task = () -> System.out.println("My first Virtual Thread");
        Thread.Builder threadBuilder = Thread.ofVirtual().name("my-virtual-thread");
        Thread thread = threadBuilder.start(task);
        thread.join(); // awaits termination
        System.out.println("The thread %s has finished.".formatted(thread.getName()));
    }

    public void createVTWithExecutorsClass() throws ExecutionException, InterruptedException {

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            Future<?> result = executorService.submit(() -> System.out.println("Task 1 running from Virtual Thread"));
            result.get();

            System.out.println("Task 1 has finished.");

            result = executorService.submit(() -> System.out.println("Task 2 running from Virtual Thread"));
            result.get();

            System.out.println("Task 1 has finished.");

            // ...
        }
    }

}
