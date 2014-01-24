package it.polimi.yaba.service;

import it.polimi.yaba.meta.TagMeta;
import it.polimi.yaba.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class TagManagerService extends ModelManagerService<Tag> {
    private static TagManagerService instance;

    public static synchronized TagManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new TagManagerService());
    }

    private TagManagerService() {
        super(Tag.class, TagMeta.get());
    }

    @Override
    public Tag create(Map<String, Object> rawData) {
        Tag tag = new Tag();
        BeanUtil.copy(rawData, tag);

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(tag);
        transaction.commit();

        tag.setKey(key);
        return tag;
    }

    @Override
    public List<Tag> match(String query) {
        query.trim();
        List<Tag> tags = new ArrayList<Tag>();
        for (Tag t : selectAll()) {
            if (t.getName().equalsIgnoreCase(query)
                || t.getName().contains(query)
                || t.getName().contains(query.toLowerCase())
                || t.getName().contains(query.toUpperCase())) {
                tags.add(t);
            }
        }
        return tags;
    }

    @Override
    public List<Tag> search(String query) {
        query.trim();
        List<Tag> tags = new ArrayList<Tag>();
        for (Tag t : selectAll()) {
            if (t.getName().equalsIgnoreCase(query)
                || t.getName().contains(query)) {
                tags.add(t);
            }
        }
        return tags;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JSONObject generateJson(Object obj) {
        JSONObject json = new JSONObject();
        Tag tag = (Tag) obj;
        json.put("type", tag.getClass().getSimpleName());
        json.put("name", tag.getName());
        return json;
    }

    public boolean exists(String name) {
        List<Tag> tags =
            Datastore
                .query(TagMeta.get())
                .filter(TagMeta.get().name.equal(name))
                .asList();
        return !tags.isEmpty();
    }

    public Tag select(String name) {
        Tag tags =
            Datastore
                .query(TagMeta.get())
                .filter(TagMeta.get().name.equal(name))
                .asSingle();
        return tags;
    }

}
