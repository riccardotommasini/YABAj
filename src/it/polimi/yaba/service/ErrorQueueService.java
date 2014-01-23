package it.polimi.yaba.service;

import java.util.LinkedList;
import java.util.Queue;

public class ErrorQueueService {
    private static ErrorQueueService instance;

    private Queue<String> errorMessages;

    public static synchronized ErrorQueueService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new ErrorQueueService());
    }

    private ErrorQueueService() {
        this.errorMessages = new LinkedList<String>();
    }

    public void enqueue(String errorMessage) {
        errorMessages.offer(errorMessage);
    }

    public String dequeue() {
        String errorMessage = errorMessages.poll();
        if (errorMessage != null) {
            return errorMessage;
        }
        return "A generic error occurred.";
    }

    public boolean isEmpty() {
        return errorMessages.isEmpty();
    }
}
