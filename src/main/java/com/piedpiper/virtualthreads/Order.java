package com.piedpiper.virtualthreads;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Order {

    private static final Logger LOGGER = Logger.getLogger(Order.class.getName());

    private String orderId;

    public Order() {
    }

    public Optional<String> processOrder() {
        try {
            calculateTotal();
            create();
            capturePayment();
            generateInvoice();
            sendConfirmationEmail();
            return Optional.of(orderId);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error processing order. | Exception: %s".formatted(ex));
        }
        return Optional.empty();
    }

    public CompletableFuture<Optional<String>> processOrderAsync() {
        CompletableFuture<Void> calculateTotalFuture = CompletableFuture.runAsync(this::calculateTotal);

        return calculateTotalFuture.thenCompose(voidResult ->
                        CompletableFuture.runAsync(this::create)
                                .thenCompose(r ->
                                        CompletableFuture.runAsync(this::capturePayment)
                                                .thenCompose(r2 ->
                                                        CompletableFuture.runAsync(this::generateInvoice)
                                                                .thenCompose(r3 ->
                                                                        CompletableFuture.runAsync(this::sendConfirmationEmail)
                                                                )
                                                )
                                )
                ).thenApply(result -> Optional.of(orderId))
                .exceptionally(ex -> {
                    LOGGER.log(Level.SEVERE, "Error processing order. | Exception: %s".formatted(ex));
                    return Optional.empty();
                });
    }

    private void calculateTotal() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void create() {
        try {
            this.orderId = UUID.randomUUID().toString();
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void capturePayment() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateInvoice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendConfirmationEmail() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
