package it.polimi.yaba.controller.posts;

import it.polimi.yaba.model.Post;
import it.polimi.yaba.service.PostManagerService;

import java.util.List;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

public class RecentController extends Controller {
    private static PostManagerService postManager = PostManagerService.get();

    @Override
    public Navigation run() throws Exception {
        List<Post> posts = postManager.getRecents();
        requestScope("posts", posts);
        return forward("recent.jsp");
    }
}
