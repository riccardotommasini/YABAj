package it.polimi.yaba.service;

import it.polimi.yaba.meta.FellowshipMeta;
import it.polimi.yaba.model.Fellowship;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class FellowshipManagerService extends ModelManagerService<Fellowship> {

    private static FellowshipManagerService instance;

    public static synchronized FellowshipManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new FellowshipManagerService());
    }

    public FellowshipManagerService() {
        super(Fellowship.class, FellowshipMeta.get());
    }

    @Override
    public Fellowship create(Map<String, Object> rawData) {
        Fellowship fellowship = new Fellowship();
        BeanUtil.copy(rawData, fellowship);

        Shop shop = ShopManagerService.get().select((Key) rawData.get("shop"));
        fellowship.getShopRef().setModel(shop);

        User user = UserManagerService.get().select((Key) rawData.get("user"));
        fellowship.getUserRef().setModel(user);

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(fellowship);
        transaction.commit();

        fellowship.setKey(key);
        return fellowship;
    }

    @Override
    public JSONObject generateJson(Object obj) {
        throw new RuntimeException(
            "generateJson method for fellowship not implemented");
    }

    @Override
    public List<Fellowship> match(String query) {
        throw new RuntimeException(
            "match method for fellowship not implemented");
    }

    @Override
    public List<Fellowship> search(String query) {
        throw new RuntimeException(
            "search method for fellowship not implemented");
    }

    public void remove(Fellowship fellowship) {
        Transaction transaction = Datastore.beginTransaction();
        Datastore.delete(fellowship.getKey());
        transaction.commit();
    }

}
