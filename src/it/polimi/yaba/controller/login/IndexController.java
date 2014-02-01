package it.polimi.yaba.controller.login;

import it.polimi.yaba.controller.YABAController;

import org.slim3.controller.Navigation;

public class IndexController extends YABAController {

    @Override
    public Navigation run() throws Exception {
        if (DEPLOY) {
            requestScope("deploy", true);
        }
        return forward("index.jsp");
    }
}
