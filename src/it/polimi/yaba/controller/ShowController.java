package it.polimi.yaba.controller;

import it.polimi.yaba.model.Image;
import it.polimi.yaba.service.ImageManagerService;

import org.slim3.controller.Navigation;

public class ShowController extends YABAController {
    private ImageManagerService service = new ImageManagerService();

    @Override
    public Navigation run() throws Exception {
        Image data = service.getData(asKey("key"), asLong("version"));
        byte[] bytes = service.getBytes(data);
        show(data.getFileName(), bytes);
        return null;
    }
}
