package com.piedpiper.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());


    private static List<Order> generateOrders(int numOfTasks) {
        return Stream.generate(Order::new)
                .limit(numOfTasks)
                .toList();
    }

    public static void main(final String[] args) {
        var concurrencyDemo = new VirtualThreads();
        var numOfTasksToProcess = 50000;

        var start = Instant.now();
        concurrencyDemo.processOrders(generateOrders(numOfTasksToProcess));
        var end = Instant.now();
        var durationWithVirtualThreads = Duration.between(start, end).toSeconds();
        LOGGER.info("Duration with Virtual Threads %d seconds.".formatted(durationWithVirtualThreads));
    }

}
