package it.polimi.yaba.model;

import it.polimi.yaba.meta.TagAssociationMeta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Product implements Serializable, Comparable<Product> {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String name;

    private Date timestamp;

    private String price;

    private final ModelRef<Shop> shopRef = new ModelRef<Shop>(Shop.class);

    private final ModelRef<Image> imageRef = new ModelRef<Image>(Image.class);

    @Attribute(persistent = false)
    private final InverseModelListRef<TagAssociation, Product> tagListRef =
        new InverseModelListRef<TagAssociation, Product>(
            TagAssociation.class,
            TagAssociationMeta.get().productRef.getName(),
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Image getImage() {
        return getImageRef().getModel();
    }

    public ModelRef<Image> getImageRef() {
        return imageRef;
    }

    public List<TagAssociation> getTags() {
        return getTagListRef().getModelList();
    }

    public InverseModelListRef<TagAssociation, Product> getTagListRef() {
        return tagListRef;
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
        Product other = (Product) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public int compareTo(Product o) {
        if (this.timestamp == null || o.timestamp == null) {
            return 0;
        } else if (this.timestamp.after(o.timestamp)) {
            return -1;
        } else if (this.timestamp.after(o.timestamp)) {
            return 1;
        }
        return 0;
    }
}
