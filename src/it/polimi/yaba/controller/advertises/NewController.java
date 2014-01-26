package it.polimi.yaba.controller.advertises;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.meta.AdvertiseMeta;
import it.polimi.yaba.model.Advertise;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.AdvertiseManagerService;
import it.polimi.yaba.service.AdvertisedProductManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
    private static AdvertiseManagerService advertiseManager =
        AdvertiseManagerService.get();
    private static AdvertisedProductManagerService advertisedProductManager =
        AdvertisedProductManagerService.get();

    @Override
    public Navigation run() throws Exception {
        Validators validators = new Validators(request);
        AdvertiseMeta meta = AdvertiseMeta.get();

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
        Advertise advertise = advertiseManager.create(map);
        debug(this, "advertise created");
        for (Product product : advertisedProducts) {
            map = new HashMap<String, Object>();
            map.put("product", product.getKey());
            map.put("advertise", advertise.getKey());
            advertisedProductManager.create(map);
            debug(this, "advertise association with '"
                + product.getName()
                + "' created");
        }

        return redirect("/shops/profile?name=" + shop.getName());
    }
}
