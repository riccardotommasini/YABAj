package it.polimi.yaba.controller.advertisements;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.meta.AdvertisementMeta;
import it.polimi.yaba.model.Advertisement;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.AdvertisedProductManagerService;
import it.polimi.yaba.service.AdvertisementManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
    private static AdvertisementManagerService advertisementManager =
        AdvertisementManagerService.get();
    private static AdvertisedProductManagerService advertisedProductManager =
        AdvertisedProductManagerService.get();

    @Override
    public Navigation run() throws Exception {
        Validators validators = new Validators(request);
        AdvertisementMeta meta = AdvertisementMeta.get();

        validators.add("products", validators.required());
        validators.add(meta.text, validators.required());

        if (!validators.validate()) {
            return reportValidationErrors(validators.getErrors());
        }

        String[] products = request.getParameterValues("products");
        String text = asString("text");
        Shop shop =
            (Shop) RequestLocator.get().getSession().getAttribute("shop");
        List<Product> advertisedProducts = new ArrayList<Product>();
        for (Product product : shop.getProducts()) {
            for (String p : products) {
                if (product.getName().equals(p)) {
                    advertisedProducts.add(product);
                }
            }
        }

        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("text", text);
        map.put("shop", shop.getKey());
        Advertisement advertisement = advertisementManager.create(map);
        debug(this, "advertisement created");
        for (Product product : advertisedProducts) {
            map = new HashMap<String, Object>();
            map.put("product", product.getKey());
            map.put("advertisement", advertisement.getKey());
            advertisedProductManager.create(map);
            debug(this, "advertisement association with '"
                + product.getName()
                + "' created");
        }

        return redirect("/shops/profile?name=" + shop.getName());
    }
}
