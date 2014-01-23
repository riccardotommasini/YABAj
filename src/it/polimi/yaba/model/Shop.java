package it.polimi.yaba.model;

import it.polimi.yaba.meta.AdvertiseMeta;
import it.polimi.yaba.meta.FellowshipMeta;
import it.polimi.yaba.meta.PlaceMeta;
import it.polimi.yaba.meta.ProductMeta;

import java.io.Serializable;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String name, email, password;

    @Attribute(persistent = false)
    private final InverseModelListRef<Place, Shop> placeListRef =
        new InverseModelListRef<Place, Shop>(
            Place.class,
            PlaceMeta.get().shopRef.getName(),
            this);

    @Attribute(persistent = false)
    private final InverseModelListRef<Advertise, Shop> advertiseListRef =
        new InverseModelListRef<Advertise, Shop>(
            Advertise.class,
            AdvertiseMeta.get().shopRef.getName(),
            this);

    @Attribute(persistent = false)
    private final InverseModelListRef<Product, Shop> productListRef =
        new InverseModelListRef<Product, Shop>(
            Product.class,
            ProductMeta.get().shopRef.getName(),
            this);

    @Attribute(persistent = false)
    private final InverseModelListRef<Fellowship, Shop> followerListRef =
        new InverseModelListRef<Fellowship, Shop>(
            Fellowship.class,
            FellowshipMeta.get().shopRef.getName(),
            this);

    private final ModelRef<Image> imageRef = new ModelRef<Image>(Image.class);

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

    public List<Place> getPlaces() {
        return getPlaceListRef().getModelList();
    }

    public InverseModelListRef<Place, Shop> getPlaceListRef() {
        return placeListRef;
    }

    public List<Advertise> getAdvertise() {
        return getAdvertiseListRef().getModelList();
    }

    public InverseModelListRef<Advertise, Shop> getAdvertiseListRef() {
        return advertiseListRef;
    }

    public Image getImage() {
        return getImageRef().getModel();
    }

    public ModelRef<Image> getImageRef() {
        return imageRef;
    }

    public List<Product> getProducts() {
        return getProductListRef().getModelList();
    }

    public InverseModelListRef<Product, Shop> getProductListRef() {
        return productListRef;
    }

    public List<Fellowship> getFollowers() {
        return getFollowerListRef().getModelList();
    }

    public InverseModelListRef<Fellowship, Shop> getFollowerListRef() {
        return followerListRef;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        Shop other = (Shop) obj;
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
