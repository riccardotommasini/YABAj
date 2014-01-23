package it.polimi.yaba.service;

import it.polimi.yaba.meta.CoordinateMeta;
import it.polimi.yaba.model.Coordinate;
import it.polimi.yaba.model.Place;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

public class CoordinateManagerService extends ModelManagerService<Coordinate> {
    private static CoordinateManagerService instance;

    public static synchronized CoordinateManagerService get() {
        if (instance != null) {
            return instance;
        }
        return (instance = new CoordinateManagerService());
    }

    private CoordinateManagerService() {
        super(Coordinate.class, CoordinateMeta.get());
    }

    @Override
    public Coordinate create(Map<String, Object> rawData) {
        Coordinate coordinate = new Coordinate();
        BeanUtil.copy(rawData, coordinate);

        Place place =
            PlaceManagerService.get().select((Key) rawData.get("place"));
        coordinate.getPlaceRef().setModel(place);

        Transaction transaction = Datastore.beginTransaction();
        Key key = Datastore.put(coordinate);
        transaction.commit();

        coordinate.setKey(key);
        return coordinate;
    }

    @Override
    public List<Coordinate> match(String query) {
        throw new RuntimeException(
            "match method for coordinates not implemented");
    }

    @Override
    public List<Coordinate> search(String query) {
        throw new RuntimeException(
            "search method for coordinates not implemented");
    }

    @Override
    public JSONObject generateJson(Object obj) {
        throw new RuntimeException(
            "generateJson method for coordinates not implemented");
    }

    public boolean exists(Float latitude, Float longitude) {
        List<Coordinate> coordinates =
            Datastore
                .query(CoordinateMeta.get())
                .filter(CoordinateMeta.get().latitude.equal(latitude))
                .filter(CoordinateMeta.get().longitude.equal(longitude))
                .asList();
        return !coordinates.isEmpty();
    }

    public List<Coordinate> select(Float latitude, Float longitude) {
        List<Coordinate> coordinates =
            Datastore
                .query(CoordinateMeta.get())
                .filter(CoordinateMeta.get().latitude.equal(latitude))
                .filter(CoordinateMeta.get().longitude.equal(longitude))
                .asList();
        return coordinates;
    }

    public Coordinate selectNearest(Float latitude, Float longitude) {
        List<Coordinate> coords = selectAll();
        if (coords.isEmpty()) {
            return null;
        }
        Coordinate test = new Coordinate();
        test.setLatitude(latitude);
        test.setLongitude(longitude);
        Coordinate nearest = coords.get(0);
        Double lowestDistance = new Double(100);
        for (Coordinate coord : coords) {
            if (getDistance(test, coord) < lowestDistance) {
                nearest = coord;
            }
        }
        return nearest;
    }

    private Double getDistance(Coordinate start, Coordinate end) {
        double d2r = Math.PI / 180;
        double dlongitude = (end.getLongitude() - start.getLongitude()) * d2r;
        double dlatitude = (end.getLatitude() - start.getLatitude()) * d2r;
        double a =
            Math.pow(Math.sin(dlatitude / 2.0), 2)
                + Math.cos(start.getLatitude() * d2r)
                * Math.cos(end.getLongitude() * d2r)
                * Math.pow(Math.sin(dlongitude / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = 6367 * c;

        return distance;
    }

}
