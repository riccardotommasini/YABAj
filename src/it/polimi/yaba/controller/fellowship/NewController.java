package it.polimi.yaba.controller.fellowship;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Fellowship;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.FellowshipManagerService;
import it.polimi.yaba.service.ShopManagerService;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
    private static ShopManagerService shopManager = ShopManagerService.get();
    private static FellowshipManagerService fellowshipManager =
        FellowshipManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String shopName = asString("shop");
        Shop shop = shopManager.select(shopName);
        if (shop == null) {
            return reportErrors("requested shop not found!");
        }
        if (RequestLocator.get().getSession().getAttribute("user") == null) {
            return reportErrors("you must be registered to follow a shop!");
        }
        User user =
            (User) RequestLocator.get().getSession().getAttribute("user");
        for (Fellowship f : shop.getFollowers()) {
            if (f.getUser().equals(user)) {
                return reportErrors("you are already a follower of this shop!");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("shop", shop.getKey());
        map.put("user", user.getKey());
        fellowshipManager.create(map);

        return redirect("/shops/profile?name=" + shopName);
    }
}
