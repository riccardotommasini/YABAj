package it.polimi.yaba.controller;

import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class IndexController extends YABAController {
    private static UserManagerService userManager = UserManagerService.get();
    private static ShopManagerService shopManager = ShopManagerService.get();

    @Override
    public Navigation run() throws Exception {
        if (RequestLocator.get().getSession().getAttribute("user") != null) {
            requestScope("logged", true);
        } else {
            requestScope("logged", false);
        }
        requestScope("users", userManager.selectAll());
        requestScope("shops", shopManager.selectAll());
        return forward("index.jsp");
    }
}
