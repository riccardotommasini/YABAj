package it.polimi.yaba.service;

import it.polimi.yaba.meta.AdvertiseMeta;
import it.polimi.yaba.meta.CoordinateMeta;
import it.polimi.yaba.model.Advertise;
import it.polimi.yaba.model.Coordinate;
import it.polimi.yaba.model.Shop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class AdvertiseManagerService extends ModelManagerService<Advertise> {
    private static AdvertiseManagerService instance;

    public static synchronized AdvertiseManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new AdvertiseManagerService());
    }

    private AdvertiseManagerService() {
        super(Advertise.class, AdvertiseMeta.get());
    }

    @Override
    public Advertise create(Map<String, Object> rawData) {
        Advertise advertise = new Advertise();
        BeanUtil.copy(rawData, advertise);

        Shop shop = ShopManagerService.get().select((Key) rawData.get("shop"));
        advertise.getShopRef().setModel(shop);

        advertise.setTimestamp(new Date());

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(advertise);
        transaction.commit();

        advertise.setKey(key);
        return advertise;
    }

    @Override
    public List<Advertise> match(String query) {
        throw new RuntimeException("match method for advertise not implemented");
    }

    @Override
    public List<Advertise> search(String query) {
        throw new RuntimeException(
            "search method for advertise not implemented");
    }

    @Override
    public JSONObject generateJson(Object obj) {
        throw new RuntimeException(
            "generateJson method for advertise not implemented");
    }

    public boolean exists(Float latitude, Float longitude) {
        List<Coordinate> coordinates =
            Datastore
                .query(CoordinateMeta.get())
                .filter(CoordinateMeta.get().latitude.equal(latitude))
                .filter(CoordinateMeta.get().longitude.equal(longitude))
                .asList();
        return !coordinates.isEmpty();
    }

}
