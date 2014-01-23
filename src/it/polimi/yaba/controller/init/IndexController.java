package it.polimi.yaba.controller.init;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Coordinate;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.CoordinateManagerService;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Navigation;

public class IndexController extends YABAController {
    private static UserManagerService userManager = UserManagerService.get();
    private static ShopManagerService shopManager = ShopManagerService.get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();
    private static CoordinateManagerService coordinateManager =
        CoordinateManagerService.get();
    private static ProductManagerService productManager = ProductManagerService
        .get();

    @Override
    public Navigation run() throws Exception {
        generateUsers();
        List<Place> places = generatePlaces();
        List<Shop> shops = generateShops(places);
        generateProducts(shops);
        return forward("/");
    }

    private List<User> generateUsers() {
        List<User> created = new ArrayList<User>();
        Map<String, Object> data;

        data = new HashMap<String, Object>();
        data.put("username", "jhonApple");
        data.put("email", "name.surname@provider.domain");
        data.put("name", "Jhon");
        data.put("surname", "Appleseed");
        data.put("password", "bomber");
        created.add(createUser(data));

        return created;
    }

    private User createUser(Map<String, Object> data) {
        if (userManager.exists((String) data.get("username"))) {
            debug(this, "USER '" + data.get("username") + "' ALREADY EXISTS");
            return null;
        }
        data.put("password", sha1((String) data.get("password")));
        User user = userManager.create(data);
        debug(this, "USER '" + data.get("username") + "' GENERATED");
        return user;
    }

    private List<Place> generatePlaces() {
        List<Place> created = new ArrayList<Place>();

        Place z1 = createPlace("Zara");
        createCoordinate(z1, new Float("45.477987"), new Float("9.2343611"));
        createCoordinate(z1, new Float("45.477900"), new Float("9.2343600"));
        created.add(z1);

        Place z2 = createPlace("Zaara");
        createCoordinate(z2, new Float("45.478000"), new Float("9.2343700"));
        created.add(z2);

        Place hem = createPlace("HeM");
        createCoordinate(hem, new Float("23.345365"), new Float("12.335745"));
        created.add(hem);

        return created;
    }

    private Place createPlace(String name) {
        if (placeManager.exists(name)) {
            debug(this, "PLACE '" + name + "' ALREADY EXISTS");
            return null;
        }
        Map<String, Object> rawData;
        rawData = new HashMap<String, Object>();
        rawData.put("name", name);
        Place place = placeManager.create(rawData);
        debug(this, "PLACE '" + name + "' GENERATED");
        return place;
    }

    private Coordinate createCoordinate(Place place, Float latitude,
            Float longitude) {
        if (place == null) {
            debug(this, "SKIP COORDINATE CREATION");
            return null;
        }
        Map<String, Object> rawData = new HashMap<String, Object>();
        rawData.put("latitude", latitude);
        rawData.put("longitude", longitude);
        rawData.put("place", place.getKey());
        Coordinate coordinate = coordinateManager.create(rawData);
        return coordinate;
    }

    private List<Shop> generateShops(List<Place> places) {
        List<Shop> created = new ArrayList<Shop>();
        List<Place> shopPlaces;
        Map<String, Object> data;

        Place z1 = null;
        Place z2 = null;
        Place h1 = null;
        for (Place p : places) {
            if (p != null) {
                if (p.getName().equals("Zara")) {
                    z1 = p;
                } else if (p.getName().equals("Zaara")) {
                    z2 = p;
                } else if (p.getName().equals("HeM")) {
                    h1 = p;
                }
            }
        }

        data = new HashMap<String, Object>();
        shopPlaces = new ArrayList<Place>();
        data.put("name", "Zara");
        data.put("email", "zara.viatorino@milano.it");
        data.put("password", "bomber");
        shopPlaces.add(z1);
        shopPlaces.add(z2);
        Shop zara = createShop(data, shopPlaces);
        created.add(zara);

        data = new HashMap<String, Object>();
        shopPlaces = new ArrayList<Place>();
        data.put("name", "HeM");
        data.put("email", "handm.viatorino@milano.it");
        data.put("password", "bomber");
        shopPlaces.add(h1);
        Shop hem = createShop(data, shopPlaces);
        created.add(hem);

        return created;
    }

    private Shop createShop(Map<String, Object> data, List<Place> places) {
        if (shopManager.exists((String) data.get("name"))) {
            debug(this, "SHOP '" + data.get("name") + "' ALREADY EXISTS");
            return null;
        }
        data.put("password", sha1((String) data.get("password")));
        Shop shop = shopManager.create(data);
        debug(this, "SHOP '" + data.get("name") + "' GENERATED");
        for (Place p : places) {
            placeManager.updateShop(p, shop);
        }
        return shop;
    }

    private List<Product> generateProducts(List<Shop> shops) {
        List<Product> created = new ArrayList<Product>();

        Shop zara = null;
        Shop hem = null;
        for (Shop s : shops) {
            if (s != null) {
                if (s.getName().equals("Zara")) {
                    zara = s;
                } else if (s.getName().equals("HeM")) {
                    hem = s;
                }
            }
        }
        created.add(createProduct("shoe", 32, zara));
        created.add(createProduct("bag", 42, zara));
        created.add(createProduct("sock", 5, zara));
        created.add(createProduct("something", 5000, hem));
        created.add(createProduct("babbarababba", 1, hem));
        created.add(createProduct("something else", 4, null));
        created.add(createProduct("gas", 34, null));
        created.add(createProduct("java virtual machine", 0, null));

        return created;

    }

    private Product createProduct(String name, int price, Shop shop) {
        if (productManager.exists(name)) {
            debug(this, "PRODUCT '" + name + "' ALREADY EXISTS");
            return null;
        }
        Map<String, Object> rawData = new HashMap<String, Object>();
        if (shop != null) {
            rawData.put("shop", shop.getKey());
        }
        rawData.put("name", name);
        rawData.put("price", price);
        Product product = productManager.create(rawData);
        debug(this, "PRODUCT '" + name + "' GENERATED");
        return product;
    }
}
