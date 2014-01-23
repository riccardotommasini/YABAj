package it.polimi.yaba.service;

import it.polimi.yaba.meta.PlaceMeta;
import it.polimi.yaba.model.Coordinate;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class PlaceManagerService extends ModelManagerService<Place> {
    private static PlaceManagerService instance;

    public static synchronized PlaceManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new PlaceManagerService());
    }

    private PlaceManagerService() {
        super(Place.class, PlaceMeta.get());
    }

    @Override
    public Place create(Map<String, Object> rawData) {
        Place place = new Place();
        BeanUtil.copy(rawData, place);

        if (rawData.containsKey("shop")) {
            Shop shop =
                ShopManagerService.get().select((Key) rawData.get("shop"));
            place.getShopRef().setModel(shop);
        }

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(place);
        transaction.commit();

        place.setKey(key);
        return place;
    }

    @Override
    public List<Place> match(String query) {
        query.trim();
        List<Place> places = new ArrayList<Place>();
        for (Place p : selectAll()) {
            if (p.getName().equalsIgnoreCase(query)
                || p.getName().contains(query)
                || p.getName().contains(query.toLowerCase())
                || p.getName().contains(query.toUpperCase())) {
                places.add(p);
            }
        }
        return places;
    }

    @Override
    public List<Place> search(String query) {
        query.trim();
        List<Place> places = new ArrayList<Place>();
        for (Place p : selectAll()) {
            if (p.getName().equalsIgnoreCase(query)
                || p.getName().contains(query)) {
                places.add(p);
            }
        }
        return places;
    }

    public boolean exists(String name) {
        Place place =
            Datastore
                .query(PlaceMeta.get())
                .filter(PlaceMeta.get().name.equal(name))
                .asSingle();
        return place != null;
    }

    public Place select(String name) {
        Place place =
            Datastore
                .query(PlaceMeta.get())
                .filter(PlaceMeta.get().name.equal(name))
                .asSingle();
        return place;
    }

    public Place selectNearest(Float latitude, Float longitude) {
        Coordinate coordinate =
            CoordinateManagerService.get().selectNearest(latitude, longitude);
        if (coordinate == null) {
            return null;
        }
        return coordinate.getPlace();
    }

    public void updateShop(Place place, Shop shop) {
        place.getShopRef().setModel(shop);
        Datastore.put(place);  
    }

}
