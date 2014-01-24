package it.polimi.yaba.controller.users;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.User;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.UserManagerService;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

public class ProfileController extends YABAController {
    public static UserManagerService userManager = UserManagerService.get();
    public static ProductManagerService productManager = ProductManagerService
        .get();

    @Override
    public Navigation run() throws Exception {

        if (RequestLocator.get().getSession().getAttribute("user") == null
            && RequestLocator.get().getSession().getAttribute("shop") == null) {
            return reportErrors("you must be registered to see this profile!");
        }
        if (!requestParameterExists("username")) {
            return reportErrors("requested user not found!");
        }
        User user = userManager.select(request.getParameter("username"));
        if (RequestLocator.get().getSession().getAttribute("user") != null) {
            User sessionUser =
                (User) RequestLocator.get().getSession().getAttribute("user");
            if (user == null) {
                // new user are eventually consistent
                user = sessionUser;
            } else if (sessionUser.getUsername().equals(user.getUsername())) {
                RequestLocator.get().getSession().setAttribute("user", user);
            }
        }
        if (user == null) {
            return reportErrors("requested user not found!");
        }

        requestScope("user", user);
        return forward("profile.jsp");
    }
}
