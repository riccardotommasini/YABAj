package it.polimi.yaba.controller.login;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.service.UserManagerService;

import java.util.HashMap;
import java.util.Map;

import org.slim3.controller.Navigation;
import org.slim3.util.RequestLocator;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.WebRequestor;
import com.restfb.types.User;

public class FbloginController extends YABAController {
    private static UserManagerService userManager = UserManagerService.get();

    @Override
    public Navigation run() throws Exception {
        String code = request.getParameter("code");

        WebRequestor wr = new DefaultWebRequestor();
        String uri;
        if (DEPLOY) {
            uri = "http://yaba-dmw.appspot.com";
        } else {
            uri = "http://localhost:8888";
        }
        WebRequestor.Response accessTokenResponse =
            wr
                .executeGet("https://graph.facebook.com/oauth/access_token?client_id="
                    + "639186146139919"
                    + "&redirect_uri="
                    + uri
                    + "/login/fblogin"
                    + "&client_secret="
                    + "59579dbe9d714d53aac86ac34b0bcc19"
                    + "&code="
                    + code);
        FacebookClient.AccessToken token =
            DefaultFacebookClient.AccessToken
                .fromQueryString(accessTokenResponse.getBody());

        String accessToken = token.getAccessToken();

        User fbUser =
            new DefaultFacebookClient(accessToken)
                .fetchObject("me", User.class);
        it.polimi.yaba.model.User yabaUser = null;
        if (userManager.select(fbUser.getUsername()) == null) {
            debug(this, "user: '" + fbUser.getUsername() + "' not exists");
            Map<String, Object> rawData = new HashMap<String, Object>();
            rawData.put("email", fbUser.getEmail());
            rawData.put("name", fbUser.getFirstName());
            rawData.put("surname", fbUser.getLastName());
            rawData.put("username", fbUser.getUsername());
            yabaUser = userManager.create(rawData);
        } else {
            yabaUser = userManager.select(fbUser.getUsername());
            debug(this, "user: '" + yabaUser.getUsername() + "' exists");
        }
        RequestLocator.get().getSession().setAttribute("user", yabaUser);
        debug(this, "session for user '" + yabaUser.getUsername() + "' setted");
        return redirect("/users/profile?username=" + yabaUser.getUsername());
    }
}
