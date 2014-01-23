package it.polimi.yaba.controller.posts;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.meta.PostMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.CoordinateManagerService;
import it.polimi.yaba.service.ImageManagerService;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.PostManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
    private static ImageManagerService imageManager = new ImageManagerService();

    private static PostManagerService postManager = PostManagerService.get();
    private static CoordinateManagerService coordinateManager =
        CoordinateManagerService.get();
    private static ShopManagerService shopManager = ShopManagerService.get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();
    private static ProductManagerService productManager = ProductManagerService
        .get();

    @Override
    public Navigation run() throws Exception {
        Validators validators = new Validators(request);
        PostMeta meta = PostMeta.get();

        validators.add("cameraInput", validators.required());
        validators.add("product", validators.required());
        validators.add("place", validators.required());
        validators.add(meta.text, validators.required());

        if (!validators.validate()) {
            return reportValidationErrors(validators.getErrors());
        }

        try {
            Long.parseLong(asString("price"));
        } catch (NumberFormatException e) {
            return reportErrors("Price must be a number!");
        }

        Map<String, Object> map;
        Place place = null;
        Shop shop = null;
        if (shopManager.exists(asString("place"))) {
            shop = shopManager.select(asString("place"));
            // take always first shop's place as default location
            place = shop.getPlaces().get(0);
            debug(this, "shop '"
                + asString("place")
                + "' exists, shop's place: '"
                + place.getName()
                + "'");
        } else if (placeManager.exists(asString("place"))) {
            debug(this, "place '" + asString("place") + "' exists");
            // select first place not associated with shop
            List<Place> possiblePlaces = placeManager.select(asString("place"));
            for (Place p : possiblePlaces) {
                if (p.getShop() == null) {
                    place = p;
                    break;
                }
            }
            debug(this, "place '" + place.getName() + "' selected");
        } else {
            // new place in users's coordinates
            map = new HashMap<String, Object>();
            map.put("name", asString("place"));
            place = placeManager.create(map);
            debug(this, "place '" + asString("place") + "' created");
            map = new HashMap<String, Object>();
            map.put("latitude", asFloat("latitude"));
            map.put("longitude", asFloat("longitude"));
            map.put("place", place.getKey());
            coordinateManager.create(map);
            debug(this, "coordinates ("
                + asFloat("latitude")
                + ", "
                + asFloat("longitude")
                + ") for: '"
                + asString("place")
                + "' created");
        }

        Product product = null;
        if (shop != null) {
            // first search for product in shops's product
            List<Product> shopProducts = shop.getProducts();
            boolean productExists = false;
            for (Product p : shopProducts) {
                if (p.getName().equals(asString("product"))) {
                    product = p;
                    productExists = true;
                    break;
                }
            }
            if (!productExists) {
                // create the product but not referenced to the shop
                debug(this, "product NOT exists in shop");
                map = new HashMap<String, Object>();
                map.put("name", asString("product"));
                product = productManager.create(map);
                debug(this, "product '" + asString("product") + "' created");
            } else {
                debug(this, "product '"
                    + product.getName()
                    + "' exists in shop");
            }
        } else if (productManager.exists(asString("product"))) {
            debug(this, "product '" + asString("product") + "' exists");
            // select first product not associated with shop
            List<Product> possibleProduct =
                productManager.select(asString("product"));
            for (Product p : possibleProduct) {
                if (p.getShop() == null) {
                    product = p;
                    break;
                }
            }
            debug(this, "product '" + product.getName() + "' selected");
        } else {
            // add new product
            map = new HashMap<String, Object>();
            map.put("name", asString("product"));
            product = productManager.create(map);
            debug(this, "product '" + asString("product") + "' created");
        }

        FileItem formImgDef = requestScope("cameraInput");
        Image imgDef = imageManager.upload(formImgDef);
        User user =
            (User) RequestLocator.get().getSession().getAttribute("user");

        map = new HashMap<String, Object>();
        map.put("text", asString("text"));
        map.put("product", product.getKey());
        map.put("productPrice", asLong("price"));
        map.put("user", user.getKey());
        map.put("place", place.getKey());
        map.put("imgDef", imgDef);
        postManager.create(map);

        return redirect("/users/profile?username=" + user.getUsername());
    }
}
