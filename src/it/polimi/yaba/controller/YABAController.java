package it.polimi.yaba.controller;

import it.polimi.yaba.service.ErrorQueueService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Errors;

public abstract class YABAController extends Controller {

    private static ErrorQueueService errorQueue = ErrorQueueService.get();

    protected boolean requestParameterExists(String name) {
        return (request.getParameter(name) != null);
    }

    protected Navigation reportErrors(List<String> errors) {
        errorQueue.enqueue("Some errors occurred:");
        for (int i = 0; i < errors.size(); i++) {
            errorQueue.enqueue(errors.get(i));
        }
        return forward("/error");
    }

    protected Navigation reportErrors(String error) {
        errorQueue.enqueue("Some errors occurred:");
        errorQueue.enqueue(error);
        return forward("/error");
    }

    protected Navigation reportValidationErrors(Errors errors) {
        errorQueue.enqueue("Some parameters were not correctly specified:");
        for (int i = 0; i < errors.size(); i++) {
            errorQueue.enqueue(errors.get(i));
        }
        return forward("/error");
    }

    protected void debug(Controller controller, String message) {
        message("DEBUG", controller, message);
    }

    protected void info(Controller controller, String message) {
        message("INFO", controller, message);
    }

    private void message(String type, Controller controller, String message) {
        Date date = new Date();
        SimpleDateFormat dateFormat =
            new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");
        System.out.println(dateFormat.format(date)
            + " "
            + controller.getClass().getName());
        System.out.println(type + ": " + message);
    }

    public String sha1(String input) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer
                .toString((result[i] & 0xff) + 0x100, 16)
                .substring(1));

        }
        return sb.toString();
    }

}
