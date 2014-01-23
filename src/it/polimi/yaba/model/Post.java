package it.polimi.yaba.model;

import it.polimi.yaba.meta.CommentMeta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class Post implements Serializable, Comparable<Post> {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String text;

    private Date timestamp;

    private Long productPrice;

    private ModelRef<Image> imageRef = new ModelRef<Image>(Image.class);

    private ModelRef<Product> productRef = new ModelRef<Product>(Product.class);

    private ModelRef<Place> placeRef = new ModelRef<Place>(Place.class);

    private ModelRef<User> userRef = new ModelRef<User>(User.class);

    @Attribute(persistent = false)
    private InverseModelListRef<Comment, Post> commentListRef =
        new InverseModelListRef<Comment, Post>(
            Comment.class,
            CommentMeta.get().postRef.getName(),
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Image getImage() {
        return getImageRef().getModel();
    }

    public ModelRef<Image> getImageRef() {
        return imageRef;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long price) {
        this.productPrice = price;
    }

    public Product getProduct() {
        return getProductRef().getModel();
    }

    public ModelRef<Product> getProductRef() {
        return productRef;
    }

    public Place getPlace() {
        return getPlaceRef().getModel();
    }

    public ModelRef<Place> getPlaceRef() {
        return placeRef;
    }

    public User getUser() {
        return getUserRef().getModel();
    }

    public ModelRef<User> getUserRef() {
        return userRef;
    }

    public List<Comment> getComments() {
        return getCommentListRef().getModelList();
    }

    public InverseModelListRef<Comment, Post> getCommentListRef() {
        return commentListRef;
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
        Post other = (Post) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    public int compareTo(Post o) {
        if (this.timestamp.after(o.timestamp)) {
            return -1;
        } else if (this.timestamp.after(o.timestamp)) {
            return 1;
        }
        return 0;
    }

}
