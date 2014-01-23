package it.polimi.yaba.service;

import it.polimi.yaba.meta.ShopMeta;
import it.polimi.yaba.model.Coordinate;
import it.polimi.yaba.model.Image;
import it.polimi.yaba.model.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class ShopManagerService extends ModelManagerService<Shop> {
	private static ShopManagerService instance;

	public static synchronized ShopManagerService get() {
		if (instance != null) {
			return instance;
		}
		return (instance = new ShopManagerService());
	}

	private ShopManagerService() {
		super(Shop.class, ShopMeta.get());
	}

	@Override
	public Shop create(Map<String, Object> rawData) {
		Shop shop = new Shop();
		BeanUtil.copy(rawData, shop);

		if (shop.getImageRef() != null) {

			Image i = (Image) rawData.get("imageRef");
			System.out.println("Image not null");
			shop.getImageRef().setModel(i);
		}
		Transaction transaction = Datastore.beginTransaction();
		Key key = Datastore.put(shop);
		transaction.commit();

		shop.setKey(key);
		return shop;
	}

	@Override
	public List<Shop> match(String query) {
		query.trim();
		List<Shop> shops = new ArrayList<Shop>();
		for (Shop s : selectAll()) {
			if (s.getName().equalsIgnoreCase(query)
					|| s.getName().contains(query)
					|| s.getName().contains(query.toLowerCase())
					|| s.getName().contains(query.toUpperCase())) {
				shops.add(s);
			}
		}
		return shops;
	}

	@Override
	public List<Shop> search(String query) {
		query.trim();
		List<Shop> shops = new ArrayList<Shop>();
		for (Shop s : selectAll()) {
			if (s.getName().equalsIgnoreCase(query)
					|| s.getName().contains(query)) {
				shops.add(s);
			} else if (s.getEmail().equalsIgnoreCase(query)
					|| s.getEmail().contains(query)) {
				shops.add(s);
			}
		}
		return shops;
	}

	public boolean exist(String name) {
		Shop shop = Datastore.query(ShopMeta.get())
				.filter(ShopMeta.get().name.equal(name)).asSingle();
		return shop != null;
	}

	public Shop select(String name) {
		Shop shop = Datastore.query(ShopMeta.get())
				.filter(ShopMeta.get().name.equal(name)).asSingle();
		return shop;
	}

	public Shop selectNearest(Float latitude, Float longitude) {
		Coordinate coordinate = CoordinateManagerService.get().selectNearest(
				latitude, longitude);
		if (coordinate == null) {
			return null;
		}
		return coordinate.getPlace().getShop();
	}

}
