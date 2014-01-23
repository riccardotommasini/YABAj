package it.polimi.yaba.controller.shops;

import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.ShopManagerService;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class ListController extends Controller {
    private static ShopManagerService shopManager = ShopManagerService.get();

    @Override
    public Navigation run() throws Exception {
        List<Shop> shops = shopManager.selectAll();
        requestScope("shops", shops);
        return forward("list.jsp");
    }
}
