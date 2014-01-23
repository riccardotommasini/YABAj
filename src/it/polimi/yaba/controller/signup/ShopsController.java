package it.polimi.yaba.controller.signup;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class ShopsController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("shops.jsp");
    }
}
