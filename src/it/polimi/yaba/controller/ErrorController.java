package it.polimi.yaba.controller;

import it.polimi.yaba.service.ErrorQueueService;

import java.util.ArrayList;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class ErrorController extends Controller {
    private ErrorQueueService errorQueue = ErrorQueueService.get();

    @Override
    public Navigation run() throws Exception {
        List<String> messages = new ArrayList<String>();

        while (!errorQueue.isEmpty()) {
            messages.add(errorQueue.dequeue());
        }

        requestScope("messages", messages);
        return forward("error.jsp");
    }
}
