package it.polimi.yaba.service;

import it.polimi.yaba.meta.UserMeta;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class UserManagerService extends ModelManagerService<User> {
    private static UserManagerService instance;

    public static synchronized UserManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new UserManagerService());
    }

    private UserManagerService() {

        super(User.class, UserMeta.get());
    }

    @Override
    public User create(Map<String, Object> rawData) {
        User user = new User();
        BeanUtil.copy(rawData, user);

        if (user.getImageRef() != null) {
            Image i = (Image) rawData.get("imageRef");
            user.getImageRef().setModel(i);
        }
        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(user);
        transaction.commit();

        user.setKey(key);
        return user;
    }

    @Override
    public List<User> match(String query) {
        throw new RuntimeException("match method for users not implemented");
    }

    @Override
    public List<User> search(String query) {
        query.trim();
        List<User> users = new ArrayList<User>();
        for (User u : selectAll()) {
            if (u.getUsername().equalsIgnoreCase(query)
                || u.getUsername().contains(query)) {
                users.add(u);
            } else if (u.getName().equalsIgnoreCase(query)
                || u.getName().contains(query)) {
                users.add(u);
            } else if (u.getSurname().equalsIgnoreCase(query)
                || u.getSurname().contains(query)) {
                users.add(u);
            } else if (u.getEmail().equalsIgnoreCase(query)
                || u.getEmail().contains(query)) {
                users.add(u);
            }
        }
        return users;
    }

    public boolean exists(String username) {
        User user =
            Datastore
                .query(UserMeta.get())
                .filter(UserMeta.get().username.equal(username))
                .asSingle();
        return user != null;
    }

    public User select(String username) {
        User user =
            Datastore
                .query(UserMeta.get())
                .filter(UserMeta.get().username.equal(username))
                .asSingle();
        return user;
    }

}
