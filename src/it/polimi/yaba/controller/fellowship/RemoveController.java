package it.polimi.yaba.controller.fellowship;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Fellowship;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.FellowshipManagerService;
import it.polimi.yaba.service.ShopManagerService;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class RemoveController extends YABAController {
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
            return reportErrors("you must be registered to unfollow a shop!");
        }
        User user =
            (User) RequestLocator.get().getSession().getAttribute("user");
        boolean isFollower = false;
        Fellowship fellowship = null;
        for (Fellowship f : shop.getFollowers()) {
            if (f.getUser().equals(user)) {
                isFollower = true;
                fellowship = f;
                break;
            }
        }
        if (!isFollower) {
            return reportErrors("you must follow this shop before unfollow it!");
        }

        fellowshipManager.remove(fellowship);

        return redirect("/shops/profile?name=" + shopName);
    }
}
