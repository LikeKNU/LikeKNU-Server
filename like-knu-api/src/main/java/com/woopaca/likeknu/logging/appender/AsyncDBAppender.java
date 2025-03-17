package com.woopaca.likeknu.logging.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncDBAppender extends AppenderBase<ILoggingEvent> {

    private static final int MAX_QUEUE_SIZE = 256;

    private final BlockingQueue<ILoggingEvent> blockingQueue = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    private ExecutorService executorService;

    @Override
    public void start() {
        super.start();
        this.executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ILoggingEvent event = blockingQueue.take();
                    // insert to db
                    String message = event.getMessage();
                    System.out.println("message = " + message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    @Override
    public void stop() {
        super.stop();
        executorService.shutdown();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        String loggerName = eventObject.getLoggerName();
        if (!"api.request".equals(loggerName)) {
            return;
        }

        boolean offer = blockingQueue.offer(eventObject);
        if (!offer) {
            System.out.println("AsyncDBAppender queue is full");
        }
    }
}
