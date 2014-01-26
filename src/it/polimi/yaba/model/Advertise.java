package it.polimi.yaba.model;

import it.polimi.yaba.meta.AdvertisedProductMeta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Advertise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String text;

    private Date timestamp;

    @Attribute(persistent = false)
    private final InverseModelListRef<AdvertisedProduct, Advertise> advertisedProductListRef =
        new InverseModelListRef<AdvertisedProduct, Advertise>(
            AdvertisedProduct.class,
            AdvertisedProductMeta.get().advertiseRef.getName(),
            this);

    private final ModelRef<Shop> shopRef = new ModelRef<Shop>(Shop.class);

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<AdvertisedProduct> getProducts() {
        return getAdvertisedProductListRef().getModelList();
    }

    public InverseModelListRef<AdvertisedProduct, Advertise> getAdvertisedProductListRef() {
        return advertisedProductListRef;
    }

    public Shop getShop() {
        return getShopRef().getModel();
    }

    public ModelRef<Shop> getShopRef() {
        return shopRef;
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
        Advertise other = (Advertise) obj;
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
