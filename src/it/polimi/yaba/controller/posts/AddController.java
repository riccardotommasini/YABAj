package it.polimi.yaba.controller.posts;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class AddController extends YABAController {
    private static ProductManagerService productManager = ProductManagerService
        .get();
    private static ShopManagerService shopManager = ShopManagerService.get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();

    @Override
    public Navigation run() throws Exception {
        if (requestParameterExists("latitude")
            && requestParameterExists("longitude")
            && RequestLocator.get().getSession().getAttribute("user") != null) {
            Float latitude = asFloat("latitude");
            Float longitude = asFloat("longitude");
            String nearestName = "";
            Object nearest = shopManager.selectNearest(latitude, longitude);
            if (nearest == null) {
                nearest = placeManager.selectNearest(latitude, longitude);
                if (nearest != null) {
                    nearestName = ((Place) nearest).getName();
                    debug(this, "nearest place is: " + nearestName);
                }
            } else {
                nearestName = ((Shop) nearest).getName();
                debug(this, "nearest shop is: " + nearestName);
            }
            requestScope("products", productManager.selectAll());
            requestScope("nearest", nearestName);
            return forward("add.jsp");
        } else {
            return reportErrors("To be able to post you need to be registrated and you must specify your position!");
        }
    }
}
