package it.polimi.yaba.controller.login;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class LoginController extends YABAController {
    private static UserManagerService userManager = UserManagerService.get();
    private static ShopManagerService shopManager = ShopManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String name = asString("username");
        String password = asString("password");
        if (userManager.exists(name)) {
            User user = userManager.select(name);
            if (user.getPassword().equals(sha1(password))) {
                RequestLocator.get().getSession().setAttribute("user", user);
                debug(this, "session for user '"
                    + user.getUsername()
                    + "' setted");
                return redirect("/users/profile?username=" + user.getUsername());
            } else {
                return reportErrors("wrong username or password");
            }
        } else if (shopManager.exist(name)) {
            Shop shop = shopManager.select(name);
            if (shop.getPassword().equals(sha1(password))) {
                RequestLocator.get().getSession().setAttribute("shop", shop);
                debug(this, "session for shop '" + shop.getName() + "' setted");
                return redirect("/shops/profile?name=" + shop.getName());
            } else {
                return reportErrors("wrong username or password");
            }
        }
        return reportErrors("wrong username or password");
    }
}
