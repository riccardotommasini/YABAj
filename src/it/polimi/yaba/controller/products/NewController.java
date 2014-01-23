package it.polimi.yaba.controller.products;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.meta.ProductMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.ImageManagerService;
import it.polimi.yaba.service.ProductManagerService;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
	private static ProductManagerService productManager = ProductManagerService
			.get();
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

		debug(this, "everything ok for new product");
		String name = asString("name");
		Shop shop = (Shop) RequestLocator.get().getSession()
				.getAttribute("shop");
		FileItem formImgDef = requestScope("cameraInput");
		Image imgDef = imageManager.upload(formImgDef);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("shop", shop.getKey());
		map.put("imgDef", imgDef);
		productManager.create(map);
		debug(this, "product created");

		return redirect("/shops/profile?name=" + shop.getName());
	}
}
