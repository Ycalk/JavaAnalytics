package source;

import java.util.HashMap;
import java.util.Map;

public class Region {
    public final double rating;
    public final String region;

    private static final Map<String, Region> regions = new HashMap<>();

    private Region(String city){
        region = RegionsData.getRegion(city);
        rating = RegionsData.getRating(region);
    }

    public static Region getRegion(String city){
        if (city == null){
            return null;
        }
        
        if (!regions.containsKey(city)){
            regions.put(city, new Region(city));
        }
        return regions.get(city);
    }
}

class RegionsData {
    private static final RegionsData instance;
    private final Map<String, String> regions = new HashMap<>();
    private final Map<String, Double> ratings = new HashMap<>();

    static{
        instance = new RegionsData();
    }

    private RegionsData(){

    }

    public static String getRegion(String cityName){
        return instance.regions.get(cityName);
    }

    public static double getRating(String regionName){
        return instance.ratings.get(regionName);
    }
}
