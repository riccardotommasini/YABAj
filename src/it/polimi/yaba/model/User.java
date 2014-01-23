package it.polimi.yaba.model;

import it.polimi.yaba.meta.CommentMeta;
import it.polimi.yaba.meta.FellowshipMeta;
import it.polimi.yaba.meta.PostMeta;

import java.io.Serializable;
import java.util.List;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model(schemaVersion = 1)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String name, surname, username, password, email;

    @Attribute(persistent = false)
    private InverseModelListRef<Post, User> postListRef =
        new InverseModelListRef<Post, User>(
            Post.class,
            PostMeta.get().userRef.getName(),
            this);

    @Attribute(persistent = false)
    private InverseModelListRef<Fellowship, User> followingListRef =
        new InverseModelListRef<Fellowship, User>(
            Fellowship.class,
            FellowshipMeta.get().userRef.getName(),
            this);

    @Attribute(persistent = false)
    private InverseModelListRef<Comment, User> commentListRef =
        new InverseModelListRef<Comment, User>(
            Comment.class,
            CommentMeta.get().userRef.getName(),
            this);

    private ModelRef<Image> imageRef = new ModelRef<Image>(Image.class);

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return getPostListRef().getModelList();
    }

    public InverseModelListRef<Post, User> getPostListRef() {
        return postListRef;
    }

    public List<Fellowship> getFollowing() {
        return getFollowingListRef().getModelList();
    }

    public InverseModelListRef<Fellowship, User> getFollowingListRef() {
        return followingListRef;
    }

    public List<Comment> getComments() {
        return getCommentListRef().getModelList();
    }

    public InverseModelListRef<Comment, User> getCommentListRef() {
        return commentListRef;
    }

    public Image getImage() {
        return getImageRef().getModel();
    }

    public ModelRef<Image> getImageRef() {
        return imageRef;
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
        User other = (User) obj;
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
