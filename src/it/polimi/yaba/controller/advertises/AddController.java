package it.polimi.yaba.controller.advertises;

import it.polimi.yaba.controller.YABAController;

import org.slim3.controller.Navigation;

public class AddController extends YABAController {

    @Override
    public Navigation run() throws Exception {
        return forward("add.jsp");
    }
}
