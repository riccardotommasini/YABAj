package it.polimi.yaba.controller.products;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.meta.ProductMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.Tag;
import it.polimi.yaba.service.ImageManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.TagAssociationManagerService;
import it.polimi.yaba.service.TagManagerService;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
    private static ProductManagerService productManager = ProductManagerService
        .get();
    private static TagManagerService tagManager = TagManagerService.get();
    private static TagAssociationManagerService tagAssociationManager =
        TagAssociationManagerService.get();
    private static ImageManagerService imageManager = new ImageManagerService();

    @Override
    public Navigation run() throws Exception {
        Validators validators = new Validators(request);
        ProductMeta meta = ProductMeta.get();
        validators.add("cameraInput", validators.required());
        validators.add(meta.name, validators.required());

        if (!validators.validate()) {
            return reportValidationErrors(validators.getErrors());
        }

        if (RequestLocator.get().getSession().getAttribute("shop") == null) {
            return reportErrors("you must be a registered shop to be able to add a produt");
        }

        String name = asString("name");
        Shop shop =
            (Shop) RequestLocator.get().getSession().getAttribute("shop");
        FileItem formImgDef = requestScope("cameraInput");
        Image imgDef = imageManager.upload(formImgDef);

        Map<String, Object> map;
        map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("shop", shop.getKey());
        map.put("imageRef", imgDef);
        Product product = productManager.create(map);

        String tagList = asString("tags");
        String[] tags = tagList.split(",");
        Tag tag;
        for (String t : tags) {
            map = new HashMap<String, Object>();
            map.put("name", t);
            tag = tagManager.create(map);
            map = new HashMap<String, Object>();
            map.put("tag", tag.getKey());
            map.put("product", product.getKey());
            tagAssociationManager.create(map);
        }

        return redirect("/shops/profile?name=" + shop.getName());
    }
}
