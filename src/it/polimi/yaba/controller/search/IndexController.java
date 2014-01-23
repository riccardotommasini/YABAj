package it.polimi.yaba.controller.search;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Post;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.PostManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slim3.controller.Navigation;

public class IndexController extends YABAController {

    private static UserManagerService userManager = UserManagerService.get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();
    private static ProductManagerService productManager = ProductManagerService
        .get();
    private static ShopManagerService shopManager = ShopManagerService.get();
    private static PostManagerService postManager = PostManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String query = asString("query");
        List<User> users = userManager.search(query);
        List<Place> places = placeManager.search(query);
        List<Product> products = productManager.search(query);
        List<Shop> shops = shopManager.search(query);
        Set<Post> postsSet = new HashSet<Post>();
        postsSet.addAll(postManager.search(query));
        postsSet.addAll(postManager.searchByPlaces(places));
        postsSet.addAll(postManager.searchByProducts(products));
        List<Post> posts = new ArrayList<Post>(postsSet);
        Collections.sort(posts);
        requestScope("users", users);
        requestScope("places", places);
        requestScope("products", products);
        requestScope("shops", shops);
        requestScope("posts", posts);
        return forward("show.jsp");
    }
}
