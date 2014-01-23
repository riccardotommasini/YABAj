package it.polimi.yaba.controller.search;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.service.ModelManagerService;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import java.io.PrintWriter;
import java.util.List;

import org.slim3.controller.Navigation;
import org.slim3.util.ResponseLocator;

public class JsonController extends YABAController {

    @Override
    public Navigation run() throws Exception {
        String query = asString("query");
        String entity = asString("entity");
        ModelManagerService<?> manager = null;

        if (entity.equals("user")) {
            manager = UserManagerService.get();
        } else if (entity.equals("shop")) {
            manager = ShopManagerService.get();
        } else if (entity.equals("product")) {
            manager = ProductManagerService.get();
        } else if (entity.equals("place")) {
            manager = PlaceManagerService.get();
        }

        List<?> entities = manager.match(query);
        String suggestions = manager.getMeta().modelsToJson(entities);
        debug(this, "query for " + entity + " recived: " + query);
        debug(this, "results: " + suggestions);
        ResponseLocator.get().setContentType("application/json");
        PrintWriter out = ResponseLocator.get().getWriter();
        out.print(suggestions);
        out.flush();
        return null;

    }

}
