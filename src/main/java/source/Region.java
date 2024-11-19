package source;

public class Region {
    private static final VkApi vkApi = new VkApi();
    public final double rating;
    public final String name;

    public Region(String studentName){
        name = vkApi.getCity(studentName);
        if (name != null){
            System.out.printf("Город: %s; Имя: %s\n", name, studentName);
        }
        rating = new RegionRating().getRating(name);
    }
}
