package source;

public class UserInfo {
    public final String city;
    public final String bDate;

    public static UserInfo empty(){
        return new UserInfo(null, null);
    }

    public UserInfo(String city, String bDate){
        this.city = city;
        this.bDate = bDate;
    }
}
