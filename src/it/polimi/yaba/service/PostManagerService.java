package it.polimi.yaba.service;

import it.polimi.yaba.meta.PostMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Post;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class PostManagerService extends ModelManagerService<Post> {
    private static PostManagerService instance;

    public static synchronized PostManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new PostManagerService());
    }

    private PostManagerService() {
        super(Post.class, PostMeta.get());
    }

    @Override
    public Post create(Map<String, Object> rawData) {
        Post post = new Post();
        BeanUtil.copy(rawData, post);

        Product product =
            ProductManagerService.get().select((Key) rawData.get("product"));
        post.getProductRef().setModel(product);

        Place place =
            PlaceManagerService.get().select((Key) rawData.get("place"));
        post.getPlaceRef().setModel(place);

        User user = UserManagerService.get().select((Key) rawData.get("user"));
        post.getUserRef().setModel(user);

        if (rawData.containsKey("price")) {
            post.setProductPrice((String) rawData.get("price"));
        }

        post.setTimestamp(new Date());

        Image i = (Image) rawData.get("imgDef");
        post.getImageRef().setModel(i);

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(post);
        transaction.commit();

        post.setKey(key);
        return post;
    }

    @Override
    public List<Post> match(String query) {
        throw new RuntimeException("match method for posts not implemented");
    }

    @Override
    public JSONObject generateJson(Object obj) {
        throw new RuntimeException(
            "generateJson method for post not implemented");
    }

    @Override
    public List<Post> search(String query) {
        query.trim();
        List<Post> posts = new ArrayList<Post>();
        for (Post p : selectAll()) {
            if (p.getText().contains(query)
                || p.getText().contains(query.toLowerCase())
                || p.getText().contains(query.toUpperCase())) {
                posts.add(p);
            }
        }
        Collections.sort(posts);
        return posts;
    }

    public List<Post> searchByProducts(List<Product> products) {
        List<Post> posts = new ArrayList<Post>();
        if (!products.isEmpty()) {
            for (Post post : selectAll()) {
                for (Product prod : products) {
                    if (post.getProduct().equals(prod)) {
                        posts.add(post);
                    }
                }
            }
        }
        return posts;
    }

    public List<Post> searchByPlaces(List<Place> places) {
        List<Post> posts = new ArrayList<Post>();
        if (!places.isEmpty()) {
            for (Place p : places) {
                posts.addAll(p.getPosts());
            }
        }
        return posts;
    }

    public List<Post> searchByUsers(List<User> users) {
        List<Post> posts = new ArrayList<Post>();
        if (!users.isEmpty()) {
            for (User u : users) {
                posts.addAll(u.getPosts());
            }
        }
        return posts;
    }

    public List<Post> getRecents() {
        List<Post> posts =
            Datastore
                .query(PostMeta.get())
                .limit(20)
                .sort(PostMeta.get().timestamp.desc)
                .asList();
        return posts;
    }

}
