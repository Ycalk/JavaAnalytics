package source;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Region {
    public final Double rating;
    public final String name;

    private static final Map<String, Region> regions = new HashMap<>();

    private Region(String name, Double rating){
        this.name = name;
        this.rating = rating;
    }

    public static Region getRegion(String city){
        if (city == null){
            return null;
        }

        if (!regions.containsKey(city)){
            var regionName = RegionsData.getRegion(city);
            if (regionName == null){
                return null;
            }
            var rating = RegionsData.getRating(regionName);
            if (rating == null){
                return null;
            }
            var newRegion = new Region(regionName, rating);
            regions.put(city, newRegion);
            return newRegion;
        }
        return regions.get(city);
    }

    @Override
    public String toString() {
        return "Region{" +
                "rating=" + rating +
                ", region='" + name + '\'' +
                '}';
    }
}

class RegionsData {
    private static final String REGIONS_FILE_NAME = "regions.json";
    private static final String RATINGS_FILE_NAME = "ratings.json";

    private static final RegionsData instance;
    private final Map<String, String> regions = new HashMap<>();
    private final Map<String, Double> ratings = new HashMap<>();

    static{
        instance = new RegionsData();
    }

    private RegionsData(){
        parseRatings(getFileString(RATINGS_FILE_NAME));
        parseRegions(getFileString(REGIONS_FILE_NAME));
    }

    public static String getRegion(String cityName){
        if (instance.regions.containsKey(cityName)){
            return instance.regions.get(cityName);
        }
        return null;
    }

    public static Double getRating(String regionName){
        if (instance.ratings.containsKey(regionName)){
            return instance.ratings.get(regionName);
        }
        return null;
    }

    private String getFileString(String fileName){
        try {
            var filePath = Path.of(
                    Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).toURI());
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseRegions(String fileString){
        var regionsArray = new JSONArray(fileString);
        for (int i = 0; i < regionsArray.length(); i++) {
            var jsonObject = regionsArray.getJSONObject(i);
            var region = jsonObject.getString("region");
            var city = jsonObject.getString("city");
            regions.put(city, region);
            ratings.putIfAbsent(region, 0.0);
        }
    }

    private void parseRatings(String fileString){
        var ratingsArray = new JSONArray(fileString);
        for (int i = 0; i < ratingsArray.length(); i++) {
            var jsonObject = ratingsArray.getJSONObject(i);
            var region = jsonObject.getString("region");
            var rating = jsonObject.getDouble("rating");
            ratings.put(region, rating);
        }
    }
}
