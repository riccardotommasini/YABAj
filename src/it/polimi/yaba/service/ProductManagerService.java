package it.polimi.yaba.service;

import it.polimi.yaba.meta.ProductMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                || p.getName().contains(query)) {
                products.add(p);
            }
        }
        return products;
    }

    public boolean exist(String name) {
        Product product =
            Datastore
                .query(ProductMeta.get())
                .filter(ProductMeta.get().name.equal(name))
                .asSingle();
        return product != null;
    }

    public Product select(String name) {
        Product product =
            Datastore
                .query(ProductMeta.get())
                .filter(ProductMeta.get().name.equal(name))
                .asSingle();
        return product;
    }

}
