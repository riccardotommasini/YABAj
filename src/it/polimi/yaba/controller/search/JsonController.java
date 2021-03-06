package it.polimi.yaba.controller.search;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.ModelManagerService;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.TagManagerService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;
import org.slim3.util.ResponseLocator;

public class JsonController extends YABAController {
    private ModelManagerService<?> manager = null;

    @Override
    @SuppressWarnings("unchecked")
    public Navigation run() throws Exception {
        String query = asString("query");
        String type = asString("type");
        debug(this, "query for '" + type + "' recived: '" + query + "'");

        JSONArray suggestions = new JSONArray();
        if (type.equals("shop")) {
            Set<JSONObject> results = new HashSet<JSONObject>();
            JSONObject json;

            List<Shop> shops = new ArrayList<Shop>();
            shops.addAll(ShopManagerService.get().search(query));
            for (Shop s : shops) {
                json = new JSONObject();
                json.put("name", s.getName());
                results.add(json);
            }

            List<Place> places = new ArrayList<Place>();
            places.addAll(PlaceManagerService.get().search(query));
            for (Place p : places) {
                json = new JSONObject();
                json.put("name", p.getName());
                results.add(json);
            }

            suggestions.addAll(results);
        } else if (type.equals("product")) {
            manager = ProductManagerService.get();
            suggestions.addAll(performSearch(query));
        } else if (type.equals("tag")) {
            manager = TagManagerService.get();
            suggestions.addAll(performSearch(query));
        } else if (type.equals("shopProducts")) {
            Shop shop =
                (Shop) RequestLocator.get().getSession().getAttribute("shop");
            for (Product p : shop.getProducts()) {
                suggestions.add(ProductManagerService.get().generateJson(p));
            }
        } else {
            return null;
        }

        debug(this, "results: " + suggestions);

        ResponseLocator.get().setContentType("application/json");
        PrintWriter out = ResponseLocator.get().getWriter();
        out.print(suggestions);
        out.flush();
        return null;

    }

    private List<JSONObject> performSearch(String query) {
        List<?> entities = manager.match(query);

        List<JSONObject> results = new ArrayList<JSONObject>();
        for (Object e : entities) {
            results.add(manager.generateJson(e));
        }

        return results;
    }

}
