package it.polimi.yaba.model;

import it.polimi.yaba.meta.CoordinateMeta;
import it.polimi.yaba.meta.PostMeta;

import java.io.Serializable;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String name;

    private ModelRef<Shop> shopRef = new ModelRef<Shop>(Shop.class);

    @Attribute(persistent = false)
    private InverseModelListRef<Coordinate, Place> coordinateListRef =
        new InverseModelListRef<Coordinate, Place>(
            Coordinate.class,
            CoordinateMeta.get().placeRef.getName(),
            this);

    @Attribute(persistent = false)
    private InverseModelListRef<Post, Place> postListRef =
        new InverseModelListRef<Post, Place>(
            Post.class,
            PostMeta.get().placeRef.getName(),
            this);

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shop getShop() {
        return getShopRef().getModel();
    }

    public ModelRef<Shop> getShopRef() {
        return shopRef;
    }

    public List<Coordinate> getCoordinates() {
        return getCoordinateListRef().getModelList();
    }

    public InverseModelListRef<Coordinate, Place> getCoordinateListRef() {
        return coordinateListRef;
    }

    public List<Post> getPosts() {
        return getPostListRef().getModelList();
    }

    public InverseModelListRef<Post, Place> getPostListRef() {
        return postListRef;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Place other = (Place) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }
}
