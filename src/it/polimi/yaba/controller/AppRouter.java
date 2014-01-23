package it.polimi.yaba.controller;

import org.slim3.controller.router.RouterImpl;

public class AppRouter extends RouterImpl {

    public AppRouter() {
        addRouting(
            "/users/profile/{username}",
            "/users/profile?username={username}");
        addRouting("/shops/profile/{name}", "/shops/profile?name={name}");
        addRouting(
            "/posts/add/{latitude}/{longitude}",
            "/posts/add?latitude={latitude}&longitude={longitude}");
    }

}
