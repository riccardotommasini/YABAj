package it.polimi.yaba.service;

import it.polimi.yaba.meta.ShopMeta;
import it.polimi.yaba.model.Advertisement;
import it.polimi.yaba.model.Coordinate;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class ShopManagerService extends ModelManagerService<Shop> {
    private static ShopManagerService instance;

    public static synchronized ShopManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new ShopManagerService());
    }

    private ShopManagerService() {
        super(Shop.class, ShopMeta.get());
    }

    @Override
    public Shop create(Map<String, Object> rawData) {
        Shop shop = new Shop();
        BeanUtil.copy(rawData, shop);

        if (shop.getImageRef() != null) {

            Image i = (Image) rawData.get("imageRef");
            shop.getImageRef().setModel(i);
        }
        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(shop);
        transaction.commit();

        shop.setKey(key);
        return shop;
    }

    @Override
    public List<Shop> match(String query) {
        query.trim();
        List<Shop> shops = new ArrayList<Shop>();
        for (Shop s : selectAll()) {
            if (s.getName().equalsIgnoreCase(query)
                || s.getName().contains(query)
                || s.getName().contains(query.toLowerCase())
                || s.getName().contains(query.toUpperCase())) {
                shops.add(s);
            }
        }
        return shops;
    }

    @Override
    public List<Shop> search(String query) {
        query.trim();
        List<Shop> shops = new ArrayList<Shop>();
        for (Shop s : selectAll()) {
            if (s.getName().equalsIgnoreCase(query)
                || s.getName().contains(query.toLowerCase())
                || s.getName().contains(query.toUpperCase())) {
                shops.add(s);
            } else if (s.getEmail().equalsIgnoreCase(query)
                || s.getEmail().contains(query.toLowerCase())
                || s.getEmail().contains(query.toUpperCase())) {
                shops.add(s);
            }
        }
        return shops;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject generateJson(Object obj) {
        JSONObject json = new JSONObject();
        Shop shop = (Shop) obj;
        // json.put("type", shop.getClass().getSimpleName());
        json.put("name", shop.getName());
        return json;
    }

    public boolean exists(String name) {
        name.trim();
        Shop shop =
            Datastore
                .query(ShopMeta.get())
                .filter(ShopMeta.get().name.equal(name))
                .asSingle();
        return shop != null;
    }

    public Shop select(String name) {
        name.trim();
        Shop shop =
            Datastore
                .query(ShopMeta.get())
                .filter(ShopMeta.get().name.equal(name))
                .asSingle();
        return shop;
    }

    public Shop selectNearest(Float latitude, Float longitude) {
        Coordinate coordinate =
            CoordinateManagerService.get().selectNearest(latitude, longitude);
        if (coordinate == null) {
            return null;
        }
        return coordinate.getPlace().getShop();
    }

    public List<Advertisement> getRecentAdvertisements(Shop shop) {
        List<Advertisement> advertisements = shop.getAdvertisements();
        Collections.sort(advertisements);
        int max = 10;
        if (max > advertisements.size()) {
            max = advertisements.size();
        }
        return new ArrayList<Advertisement>(advertisements.subList(0, max));
    }

    public List<Product> getRecentProducts(Shop shop) {
        List<Product> products = shop.getProducts();
        Collections.sort(products);
        int max = 4;
        if (max > products.size()) {
            max = products.size();
        }
        return new ArrayList<Product>(products.subList(0, max));
    }

}
