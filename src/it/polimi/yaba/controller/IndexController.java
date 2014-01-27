package it.polimi.yaba.controller;

import it.polimi.yaba.model.Advertise;
import it.polimi.yaba.model.Fellowship;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.UserManagerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class IndexController extends YABAController {
    private static UserManagerService userManager = UserManagerService.get();

    @Override
    public Navigation run() throws Exception {
        if (RequestLocator.get().getSession().getAttribute("shop") != null) {
            Shop shop =
                (Shop) RequestLocator.get().getSession().getAttribute("shop");
            return forward("/shops/profile?name=" + shop.getName());
        }
        if (RequestLocator.get().getSession().getAttribute("user") != null) {
            User user =
                (User) RequestLocator.get().getSession().getAttribute("user");
            user = userManager.select(user.getUsername());
            RequestLocator.get().getSession().setAttribute("user", user);
            List<Advertise> advertises = new ArrayList<Advertise>();
            for (Fellowship f : user.getFollowing()) {
                debug(this, "user '"
                    + f.getUser().getName()
                    + "' following '"
                    + f.getShop().getName()
                    + "'");
                advertises.addAll(f.getShop().getAdvertises());
            }
            Collections.sort(advertises);
            requestScope("advertises", advertises);
            return forward("index.jsp");
        }
        return forward("index.jsp");
    }
}
