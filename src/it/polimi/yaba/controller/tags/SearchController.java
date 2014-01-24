package it.polimi.yaba.controller.tags;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.TagManagerService;

import java.util.List;

import org.slim3.controller.Navigation;

public class SearchController extends YABAController {
    private static ProductManagerService productManager = ProductManagerService
        .get();
    private static TagManagerService tagManager = TagManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String query = asString("name");
        if (query == null || query.equals("")) {
            return reportErrors("you must specify a tag!");
        }
        if (!tagManager.exists(query)) {
            return reportErrors("not a valid tag name");
        }
        List<Product> products = productManager.searchByTag(query);
        requestScope("products", products);
        return forward("list.jsp");
    }
}
