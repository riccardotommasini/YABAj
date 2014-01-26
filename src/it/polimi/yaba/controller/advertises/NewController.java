package it.polimi.yaba.controller.advertises;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.meta.AdvertiseMeta;
import it.polimi.yaba.model.Shop;

import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {

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

        String debugList = "";
        for (String product : products) {
            debugList += product + " ";
        }
        debug(this, "received products: " + debugList);

        Shop shop =
            (Shop) RequestLocator.get().getSession().getAttribute("shop");
        return redirect("/shops/profile?name=" + shop.getName());
    }
}
