package it.polimi.yaba.controller;

import it.polimi.yaba.model.Shop;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class IndexController extends YABAController {

    @Override
    public Navigation run() throws Exception {
        if (RequestLocator.get().getSession().getAttribute("shop") != null) {
            Shop shop =
                (Shop) RequestLocator.get().getSession().getAttribute("user");
            return forward("/shops/profile?name=" + shop.getName());
        }
        return forward("index.jsp");
    }
}
