package it.polimi.yaba.controller.places;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Post;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.PostManagerService;
import it.polimi.yaba.service.ProductManagerService;

import java.util.List;

import org.slim3.controller.Navigation;

public class SearchController extends YABAController {
    private static ProductManagerService productManager = ProductManagerService
        .get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();
    private static PostManagerService postManager = PostManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String query = asString("name");
        if (query == null || query.equals("")) {
            return reportErrors("you must specify a shop!");
        }
        if (!placeManager.exists(query)) {
            return reportErrors("not a valid shop name");
        }
        List<Place> places = placeManager.select(query);
        List<Post> posts = postManager.searchByPlaces(places);
        List<Product> products = productManager.searchByPlace(places);
        requestScope("posts", posts);
        requestScope("products", products);
        return forward("list.jsp");
    }
}
