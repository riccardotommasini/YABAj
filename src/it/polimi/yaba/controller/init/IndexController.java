package it.polimi.yaba.controller.init;

import it.polimi.yaba.controller.YABAController;
import it.polimi.yaba.model.Place;
import it.polimi.yaba.model.Shop;
import it.polimi.yaba.service.CoordinateManagerService;
import it.polimi.yaba.service.PlaceManagerService;
import it.polimi.yaba.service.ProductManagerService;
import it.polimi.yaba.service.ShopManagerService;
import it.polimi.yaba.service.UserManagerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slim3.controller.Navigation;

public class IndexController extends YABAController {
	private static UserManagerService userManager = UserManagerService.get();
	private static ShopManagerService shopManager = ShopManagerService.get();
	private static PlaceManagerService placeManager = PlaceManagerService.get();
	private static CoordinateManagerService coordinateManager = CoordinateManagerService
			.get();
	private static ProductManagerService productManager = ProductManagerService
			.get();

	@Override
	public Navigation run() throws Exception {
		generateUsers();
		generatePlaces();
		generateShopsAndProducts();
		return forward("/");
	}

	private void generateUsers() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("username", "jhonApple");
		data.put("email", "name.surname@provider.domain");
		data.put("name", "Jhon");
		data.put("surname", "Appleseed");
		data.put("password", "bomber");
		createUser(data);
	}

	private void createUser(Map<String, Object> data) {
		if (userManager.select((String) data.get("username")) == null) {
			data.put("password", sha1((String) data.get("password")));
			userManager.create(data);
			debug(this, "user " + data.get("username") + " generated");
		} else {
			debug(this, "user " + data.get("username") + " already exists");
		}
	}

	private void generatePlaces() {
		Place z1 = generatePlace("Zara");
		generateCoordinate(z1, new Float("45.477987"), new Float("9.2343611"));
		generateCoordinate(z1, new Float("45.477900"), new Float("9.2343600"));
		Place z2 = generatePlace("Zaara");
		generateCoordinate(z2, new Float("45.478000"), new Float("9.2343700"));
		Place hem = generatePlace("HeM");
		generateCoordinate(hem, new Float("23.345365"), new Float("12.335745"));
	}

	private Place generatePlace(String name) {
		if (placeManager.select(name) == null) {
			Map<String, Object> rawData;
			rawData = new HashMap<String, Object>();
			rawData.put("name", name);
			Place place = placeManager.create(rawData);
			debug(this, "place " + name + " generated");
			return place;
		} else {
			debug(this, "place " + name + " already exists");
			return null;
		}
	}

	private void generateCoordinate(Place place, Float latitude, Float longitude) {
		if (place == null) {
			debug(this, "place already exists, skip coordinate creation");
			return;
		}
		Map<String, Object> rawData = new HashMap<String, Object>();
		rawData.put("latitude", latitude);
		rawData.put("longitude", longitude);
		rawData.put("place", place.getKey());
		coordinateManager.create(rawData);
	}

	private void generateShopsAndProducts() {
		Map<String, Object> data;
		List<String> places;
		data = new HashMap<String, Object>();
		data.put("name", "Zara");
		data.put("email", "zara.viatorino@milano.it");
		data.put("password", "bomber");
		places = new ArrayList<String>();
		places.add("Zara");
		places.add("Zaara");
		Shop zara = createShop(data, places);
		data = new HashMap<String, Object>();
		data.put("name", "HeM");
		data.put("email", "handm.viatorino@milano.it");
		data.put("password", "bomber");
		places = new ArrayList<String>();
		places.add("HeM");
		Shop hem = createShop(data, places);

		createProduct("shoe", 32, zara);
		createProduct("bag", 42, zara);
		createProduct("sock", 5, zara);
		createProduct("something", 5000, hem);
		createProduct("something else", 4, null);
		createProduct("babbarababba", 1, hem);
		createProduct("gas", 34, null);
		createProduct("java virtual machine", 0, null);
	}

	private Shop createShop(Map<String, Object> data, List<String> places) {
		Shop shop = null;
		if (shopManager.select((String) data.get("name")) == null) {
			data.put("password", sha1((String) data.get("password")));
			shop = shopManager.create(data);
			debug(this, "shop " + data.get("name") + " generated");
			for (String p : places) {
				Place place = placeManager.select(p);
				placeManager.updateShop(place, shop);
			}
		} else {
			debug(this, "shop " + data.get("name") + " already exists");
		}
		return shop;
	}

	private void createProduct(String name, int price, Shop shop) {
		if (productManager.select(name) == null) {
			Map<String, Object> rawData = new HashMap<String, Object>();
			if (shop != null) {
				rawData.put("shop", shop.getKey());
			}
			rawData.put("name", name);
			rawData.put("price", price);
			productManager.create(rawData);
			debug(this, "product " + name + " generated");
		} else {
			debug(this, "product " + name + " already exists");
		}

	}
}
