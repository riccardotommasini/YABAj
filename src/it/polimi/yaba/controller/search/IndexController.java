package it.polimi.yaba.controller.search;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Tag;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.TagManagerService;
import it.polimi.yaba.service.UserManagerService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slim3.controller.Navigation;

public class IndexController extends YABAController {

    private static UserManagerService userManager = UserManagerService.get();
    private static TagManagerService tagManager = TagManagerService.get();
    private static ProductManagerService productManager = ProductManagerService
        .get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String query = asString("query");
        List<Product> products;
        List<Tag> tags;
        List<Place> places;
        List<User> users;
        if (query == null || query.equals("")) {
            debug(this, "select all");
            products = productManager.selectAll();
            places = placeManager.selectAll();
        }
        Set<Product> p = new HashSet<Product>();
        p.addAll(productManager.search(query));
        tags = tagManager.search(query);
        p.addAll(productManager.searchByTag(tags));
        products = new ArrayList<Product>(p);
        places = placeManager.search(query);
        users = userManager.search(query);

        requestScope("products", products);
        requestScope("places", places);
        requestScope("users", users);
        return forward("show.jsp");
    }
}
