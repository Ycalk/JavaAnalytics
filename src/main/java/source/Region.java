package source;

public class Region {
    private static final VkApi vkApi = new VkApi();
    public final double rating;
    public final String name;

    public Region(String studentName){
        var region = vkApi.getRegion(studentName);
        rating = new RegionRating().getRating(region);
        name = region;
    }
}
