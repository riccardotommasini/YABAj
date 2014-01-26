package it.polimi.yaba.service;

import it.polimi.yaba.meta.TagAssociationMeta;
import it.polimi.yaba.model.Product;
import it.polimi.yaba.model.Tag;
import it.polimi.yaba.model.TagAssociation;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class TagAssociationManagerService extends
        ModelManagerService<TagAssociation> {

    private static TagAssociationManagerService instance;

    public static synchronized TagAssociationManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new TagAssociationManagerService());
    }

    public TagAssociationManagerService() {
        super(TagAssociation.class, TagAssociationMeta.get());
    }

    @Override
    public TagAssociation create(Map<String, Object> rawData) {
        TagAssociation tagAssociation = new TagAssociation();

        Product product =
            ProductManagerService.get().select((Key) rawData.get("product"));
        tagAssociation.getProductRef().setModel(product);

        Tag tag = TagManagerService.get().select((Key) rawData.get("tag"));
        tagAssociation.getTagRef().setModel(tag);

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(tagAssociation);
        transaction.commit();

        tagAssociation.setKey(key);
        return tagAssociation;
    }

    @Override
    public JSONObject generateJson(Object obj) {
        throw new RuntimeException(
            "generateJson method for tagAssociation not implemented");
    }

    @Override
    public List<TagAssociation> match(String query) {
        throw new RuntimeException(
            "match method for tagAssociation not implemented");
    }

    @Override
    public List<TagAssociation> search(String query) {
        throw new RuntimeException(
            "search method for tagAssociation not implemented");
    }
}
