package it.polimi.yaba.controller.products;

import it.polimi.yaba.model.Product;
import it.polimi.yaba.service.ProductManagerService;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class ListController extends Controller {
    private static ProductManagerService productManager = ProductManagerService
        .get();

    @Override
    public Navigation run() throws Exception {
        List<Product> products = productManager.selectAll();
        requestScope("products", products);
        return forward("list.jsp");
    }
}
