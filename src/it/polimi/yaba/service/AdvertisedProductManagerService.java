package it.polimi.yaba.service;

import it.polimi.yaba.meta.AdvertisedProductMeta;
import it.polimi.yaba.model.AdvertisedProduct;
import it.polimi.yaba.model.Advertisement;
import it.polimi.yaba.model.Product;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class AdvertisedProductManagerService extends
        ModelManagerService<AdvertisedProduct> {

    private static AdvertisedProductManagerService instance;

    public static synchronized AdvertisedProductManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new AdvertisedProductManagerService());
    }

    public AdvertisedProductManagerService() {
        super(AdvertisedProduct.class, AdvertisedProductMeta.get());
    }

    @Override
    public AdvertisedProduct create(Map<String, Object> rawData) {
        AdvertisedProduct advertisedProduct = new AdvertisedProduct();

        Product product =
            ProductManagerService.get().select((Key) rawData.get("product"));
        advertisedProduct.getProductRef().setModel(product);

        Advertisement advertise =
            AdvertisementManagerService.get().select(
                (Key) rawData.get("advertisement"));
        advertisedProduct.getAdvertiseRef().setModel(advertise);

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(advertisedProduct);
        transaction.commit();

        advertisedProduct.setKey(key);
        return advertisedProduct;
    }

    @Override
    public JSONObject generateJson(Object obj) {
        throw new RuntimeException(
            "generateJson method for advertisedProduct not implemented");
    }

    @Override
    public List<AdvertisedProduct> match(String query) {
        throw new RuntimeException(
            "match method for advertisedProduct not implemented");
    }

    @Override
    public List<AdvertisedProduct> search(String query) {
        throw new RuntimeException(
            "search method for advertisedProduct not implemented");
    }
}
