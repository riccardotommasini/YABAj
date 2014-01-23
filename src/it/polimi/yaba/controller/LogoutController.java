package it.polimi.yaba.controller;

import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class LogoutController extends YABAController {

    @Override
    public Navigation run() throws Exception {
        if (RequestLocator.get().getSession().getAttribute("user") != null) {
            User user =
                (User) RequestLocator.get().getSession().getAttribute("user");
            debug(this, "logout: '" + user.getUsername() + "'");
        } else if (RequestLocator.get().getSession().getAttribute("shop") != null) {
            Shop shop =
                (Shop) RequestLocator.get().getSession().getAttribute("shop");
            debug(this, "logout: '" + shop.getName() + "'");
        } else {
            debug(this, "logout but no one was logged!");
        }
        RequestLocator.get().getSession().invalidate();
        return redirect("/");
    }
}
