package it.polimi.yaba.controller.signup;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.ImageManagerService;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.controller.upload.FileItem;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestLocator;

public class NewController extends YABAController {
    private static ShopManagerService shopManager = ShopManagerService.get();
    private static UserManagerService userManager = UserManagerService.get();
    private static PlaceManagerService placeManager = PlaceManagerService.get();
    private static ImageManagerService imageManager = new ImageManagerService();

    @Override
    public Navigation run() throws Exception {
        Validators validators = new Validators(request);

        validators.add("type", validators.required());

        if (!validators.validate()) {
            return reportValidationErrors(validators.getErrors());
        }

        String type = asString("type");
        if (type.equals("user")) {
            validators.add("username", validators.required());
            validators.add("name", validators.required());
            validators.add("surname", validators.required());
            validators.add("email", validators.required());
            validators.add("password", validators.required());
            validators.add("password-confirm", validators.required());

            if (!validators.validate()) {
                return reportValidationErrors(validators.getErrors());
            }

            String username = asString("username");
            if (shopManager.select(username) != null) {
                return reportErrors("username already exist!");
            }
            String name = asString("name");
            String surname = asString("surname");
            String email = asString("email");
            String password = asString("password");
            String confirm = asString("password-confirm");

            if (!password.equals(confirm)) {
                return reportErrors("password do not match");
            }

            Image imgDef = null;
            if (requestScope("cameraInput") != null) {
                FileItem formImgDef = requestScope("cameraInput");
                imgDef = imageManager.upload(formImgDef);
            }

            Map<String, Object> rawData;
            rawData = new HashMap<String, Object>();
            rawData.put("name", name);
            rawData.put("username", username);
            rawData.put("surname", surname);
            rawData.put("imageRef", imgDef);
            rawData.put("password", sha1(password));
            rawData.put("email", email);

            User user = userManager.create(rawData);

            RequestLocator.get().getSession().setAttribute("user", user);
            debug(this, "session for user '" + user.getUsername() + "' setted");
            return redirect("/users/profile?username=" + user.getUsername());

        } else if (type.equals("shop")) {
            validators.add("name", validators.required());
            validators.add("email", validators.required());
            validators.add("password", validators.required());
            validators.add("password-confirm", validators.required());

            if (!validators.validate()) {
                return reportValidationErrors(validators.getErrors());
            }

            String name = asString("name");
            if (shopManager.select(name) != null) {
                return reportErrors("name already exist!");
            }
            String email = asString("email");
            String password = asString("password");
            String confirm = asString("password-confirm");

            if (!password.equals(confirm)) {
                return reportErrors("password do not match");
            }

            Image imgDef = null;
            if (requestScope("cameraInput") != null) {
                FileItem formImgDef = requestScope("cameraInput");
                imgDef = imageManager.upload(formImgDef);
            }

            Map<String, Object> rawData;
            rawData = new HashMap<String, Object>();
            rawData.put("name", name);
            rawData.put("email", email);
            rawData.put("password", sha1(password));
            rawData.put("imageRef", imgDef);

            Shop shop = shopManager.create(rawData);

            // create a default place with same name
            rawData = new HashMap<String, Object>();
            rawData.put("name", name);
            rawData.put("shop", shop.getKey());
            placeManager.create(rawData);

            RequestLocator.get().getSession().setAttribute("shop", shop);
            debug(this, "session for shop '" + shop.getName() + "' setted");
            return forward("/shops/profile?name=" + shop.getName());
        }

        return reportErrors("unrecognized request");
    }
}
