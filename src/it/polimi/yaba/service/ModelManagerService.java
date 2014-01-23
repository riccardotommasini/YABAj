package it.polimi.yaba.service;

import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.datastore.EntityNotFoundRuntimeException;
import org.slim3.datastore.ModelMeta;

import com.google.appengine.api.datastore.Key;

public abstract class ModelManagerService<T> {
    private Class<T> type;
    private ModelMeta<T> meta;

    public ModelManagerService(Class<T> type, ModelMeta<T> meta) {
        this.type = type;
        this.meta = meta;
    }

    public ModelMeta<T> getMeta() {
        return meta;
    }

    abstract public T create(Map<String, Object> rawData);

    public T select(Key key) {
        try {
            return Datastore.get(type, key);
        } catch (EntityNotFoundRuntimeException enfrex) {
            return null;
        } catch (IllegalArgumentException iaex) {
            return null;
        }
    }

    public List<T> selectAll() {
        return Datastore.query(meta).asList();
    }

    /**
     * 
     * @param query
     * @return list of object matching the query for suggestion purpose
     */
    abstract public List<T> match(String query);

    /**
     * 
     * @param query
     * @return list of object matching the query for search purpose
     */
    abstract public List<T> search(String query);

}
