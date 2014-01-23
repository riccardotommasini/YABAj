package it.polimi.yaba.model;

import java.io.Serializable;
import java.net.URL;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Key;

@Model
public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	@Attribute(primaryKey = true)
	private Key key;

	@Attribute(version = true)
	private Long version = 0L;

	private String fileName;

	private FileTypes type;

	private String refName;

	private int length;

	@Attribute(lob = true)
	private URL url;

	@Attribute(persistent = false)
	private InverseModelListRef<ImageFragment, Image> fragmentListRef = new InverseModelListRef<ImageFragment, Image>(
			ImageFragment.class, "imageRef", this, new Sort("index"));

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * @return the fragmentListRef
	 */
	public InverseModelListRef<ImageFragment, Image> getFragmentListRef() {
		return fragmentListRef;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public FileTypes getType() {
		return type;
	}

	public void setType(FileTypes type) {
		this.type = type;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

}