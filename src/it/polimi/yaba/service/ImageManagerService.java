package it.polimi.yaba.service;

import it.polimi.yaba.meta.ImageFragmentMeta;
import it.polimi.yaba.meta.ImageMeta;
import it.polimi.yaba.model.FileTypes;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.ImageFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.slim3.controller.upload.FileItem;
import org.slim3.datastore.Datastore;
import org.slim3.util.ByteUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class ImageManagerService {

	private static final int FRAGMENT_SIZE = 900000;

	private ImageMeta d = ImageMeta.get();

	private ImageFragmentMeta f = ImageFragmentMeta.get();

	public List<Image> getDataList() {
		return Datastore.query(d).asList();
	}

	public Image upload(FileItem formFile) {
		if (formFile == null) {
			return null;
		}

		List<Object> models = new ArrayList<Object>();
		Image data = new Image();
		models.add(data);
		data.setKey(Datastore.allocateId(d));
		data.setFileName(formFile.getShortFileName());
		typify(formFile, data);
		data.setLength(formFile.getData().length);
		byte[] bytes = formFile.getData();
		byte[][] bytesArray = ByteUtil.split(bytes, FRAGMENT_SIZE);
		Iterator<Key> keys = Datastore.allocateIds(data.getKey(), f,
				bytesArray.length).iterator();
		for (int i = 0; i < bytesArray.length; i++) {
			byte[] fragmentData = bytesArray[i];
			ImageFragment fragment = new ImageFragment();
			models.add(fragment);
			fragment.setKey(keys.next());
			fragment.setBytes(fragmentData);
			fragment.setIndex(i);
			fragment.getImageRef().setModel(data);
		}
		Transaction tx = Datastore.beginTransaction();
		for (Object model : models) {
			Datastore.put(tx, model);
		}
		tx.commit();

		return data;
	}

	private void typify(FileItem formFile, Image data) {

		String filename = formFile.getShortFileName();
		String[] strings = filename.split("\\.");
		data.setType(FileTypes.valueOf(strings[strings.length - 1]));
	}

	public Image getData(Key key, Long version) {
		return Datastore.get(d, key, version);
	}

	public byte[] getBytes(Image Image) {
		if (Image == null) {
			throw new NullPointerException(
					"The Image parameter must not be null.");
		}
		List<ImageFragment> fragmentList = Image.getFragmentListRef()
				.getModelList();
		byte[][] bytesArray = new byte[fragmentList.size()][0];
		for (int i = 0; i < fragmentList.size(); i++) {
			bytesArray[i] = fragmentList.get(i).getBytes();
		}
		return ByteUtil.join(bytesArray);
	}

	public void delete(Key key) {
		Transaction tx = Datastore.beginTransaction();
		List<Key> keys = new ArrayList<Key>();
		keys.add(key);
		keys.addAll(Datastore.query(f, key).asKeyList());
		Datastore.delete(tx, keys);
		tx.commit();
	}

	public Image getImg(Key k) {
		for (Image p : Datastore.query(ImageMeta.get()).asList()) {
			if (p.getKey().equals(k))
				return p;
		}

		throw new NoSuchElementException();
	}
}
