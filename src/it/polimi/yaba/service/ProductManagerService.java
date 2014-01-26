package it.polimi.yaba.service;

import it.polimi.yaba.meta.ProductMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.TagAssociation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class ProductManagerService extends ModelManagerService<Product> {
    private static ProductManagerService instance;

    public static synchronized ProductManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new ProductManagerService());
    }

    private ProductManagerService() {
        super(Product.class, ProductMeta.get());
    }

    @Override
    public Product create(Map<String, Object> rawData) {
        Product product = new Product();
        BeanUtil.copy(rawData, product);

        Image i = (Image) rawData.get("imageRef");
        product.getImageRef().setModel(i);

        if (rawData.containsKey("shop")) {
            Shop shop =
                ShopManagerService.get().select((Key) rawData.get("shop"));
            product.getShopRef().setModel(shop);
        }

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(product);
        transaction.commit();

        product.setKey(key);
        return product;
    }

    @Override
    public List<Product> match(String query) {
        query.trim();
        List<Product> products = new ArrayList<Product>();
        for (Product p : selectAll()) {
            if (p.getName().equalsIgnoreCase(query)
                || p.getName().contains(query)
                || p.getName().contains(query.toLowerCase())
                || p.getName().contains(query.toUpperCase())) {
                products.add(p);
            }
        }
        return products;
    }

    @Override
    public List<Product> search(String query) {
        query.trim();
        List<Product> products = new ArrayList<Product>();
        for (Product p : selectAll()) {
            if (p.getName().equalsIgnoreCase(query)
                || p.getName().contains(query)
                || p.getName().contains(query.toLowerCase())
                || p.getName().contains(query.toUpperCase())) {
                products.add(p);
            }
        }
        return products;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject generateJson(Object obj) {
        JSONObject json = new JSONObject();
        Product product = (Product) obj;
        json.put("type", product.getClass().getSimpleName());
        json.put("name", product.getName());
        return json;
    }

    public boolean exists(String name) {
        name.trim();
        List<Product> products =
            Datastore
                .query(ProductMeta.get())
                .filter(ProductMeta.get().name.equal(name))
                .asList();
        return !products.isEmpty();
    }

    public List<Product> select(String name) {
        name.trim();
        List<Product> products =
            Datastore
                .query(ProductMeta.get())
                .filter(ProductMeta.get().name.equal(name))
                .asList();
        return products;
    }

    public List<Product> searchByTag(String query) {
        List<Product> products = new ArrayList<Product>();
        for (Product p : selectAll()) {
            for (TagAssociation t : p.getTags()) {
                if (t.getTag().getName().equals(query)) {
                    products.add(p);
                }
            }
        }
        return products;
    }

}
