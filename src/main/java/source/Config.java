package source;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Config {
    private static final String FILE_NAME = "config.json";
    private static final Config instance;

    private final int APP_ID;
    private final String CLIENT_SECRET;
    private final String REDIRECT_URI;
    private final String CODE;
    private final String TOKEN;
    private final int USER_ID;

    public static int getAppId(){
        return instance.APP_ID;
    }

    public static String getClientSecret(){
        return instance.CLIENT_SECRET;
    }

    public static String getRedirectUri(){
        return instance.REDIRECT_URI;
    }

    public static String getCode(){
        return instance.CODE;
    }

    public static String getToken(){
        return instance.TOKEN;
    }

    public static int getUserId(){
        return instance.USER_ID;
    }

    static {
        instance = new Config();
    }

    private Config(){
        var fileString = getFileString();
        var config = new JSONObject(fileString);
        APP_ID = config.getInt("APP_ID");
        CLIENT_SECRET = config.getString("CLIENT_SECRET");
        REDIRECT_URI = config.getString("REDIRECT_URI");
        CODE = config.getString("CODE");
        TOKEN = config.getString("TOKEN");
        USER_ID = config.getInt("USER_ID");
    }

    private String getFileString(){
        try {
            var filePath = Path.of(
                    Objects.requireNonNull(getClass().getClassLoader().getResource(FILE_NAME)).toURI());
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
