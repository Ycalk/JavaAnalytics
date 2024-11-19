package source;

public class Region {
    public final double rating;
    public final String name;

    public Region(String studentName){
        var region = new VkApi().getRegion(studentName);
        rating = new RegionRating().getRating(region);
        name = region;
    }
}
