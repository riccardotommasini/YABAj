package it.polimi.yaba.controller.shops;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Advertise;
import it.polimi.yaba.model.Fellowship;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.ShopManagerService;

import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class ProfileController extends YABAController {

    public static ShopManagerService shopManager = ShopManagerService.get();

    @Override
    public Navigation run() throws Exception {
        if (!requestParameterExists("name")) {
            return reportErrors("requested shop not found!");
        }
        Shop shop = shopManager.select(request.getParameter("name"));
        if (RequestLocator.get().getSession().getAttribute("shop") != null) {
            Shop sessionShop =
                (Shop) RequestLocator.get().getSession().getAttribute("shop");
            if (shop == null) {
                // new shops are eventually consistent
                shop = sessionShop;
            } else if (sessionShop.getName().equals(shop.getName())) {
                RequestLocator.get().getSession().setAttribute("shop", shop);
            }
        }
        if (shop == null) {
            return reportErrors("requested shop not found!");
        }

        boolean isFollower = false;
        List<Fellowship> followers = shop.getFollowers();
        if (RequestLocator.get().getSession().getAttribute("user") != null) {
            User requester =
                (User) RequestLocator.get().getSession().getAttribute("user");
            for (Fellowship f : followers) {
                if (f.getUser().equals(requester)) {
                    isFollower = true;
                    break;
                }
            }
        }
        List<Product> products = shop.getProducts();
        List<Advertise> advertises = shop.getAdvertise();
        requestScope("shop", shop);
        requestScope("products", products);
        requestScope("advertises", advertises);
        requestScope("isFollower", isFollower);
        requestScope("followers", followers);
        return forward("profile.jsp");
    }
}
