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
    private static Config instance;

    private static int APP_ID;
    private static String CLIENT_SECRET;
    private static String REDIRECT_URI;
    private static String CODE;

    public static int getAppId(){
        return APP_ID;
    }

    public static String getClientSecret(){
        return CLIENT_SECRET;
    }

    public static String getRedirectUri(){
        return REDIRECT_URI;
    }

    public static String getCode(){
        return CODE;
    }

    public static void init(){
        if (instance == null){
            instance = new Config();
        }
    }

    private Config(){
        String fileString = getFileString();
        var config = new JSONObject(fileString);
        APP_ID = config.getInt("APP_ID");
        CLIENT_SECRET = config.getString("CLIENT_SECRET");
        REDIRECT_URI = config.getString("REDIRECT_URI");
        CODE = config.getString("CODE");
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
