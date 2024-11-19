package source;

import com.vk.api.sdk.actions.Users;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.responses.SearchResponse;

public class VkApi {
    private final UserActor actor;
    private final VkApiClient vk;

    public VkApi(){
        vk = new VkApiClient(new HttpTransportClient());
        try {
            long userId;
            String token;
            if (Config.getUserId() == 0 || Config.getToken().isEmpty()){
                var authResponse = vk.oAuth()
                        .userAuthorizationCodeFlow(Config.getAppId(), Config.getClientSecret(),
                                Config.getRedirectUri(), Config.getCode())
                        .execute();
                userId = authResponse.getUserId();
                token = authResponse.getAccessToken();
            } else {
                userId = Config.getUserId();
                token = Config.getToken();
            }

            actor = new UserActor(userId, token);
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCity(String studentName){
        SearchResponse response;
        try {
            response = new Users(vk).search(actor).q(studentName).fields(Fields.CITY, Fields.HOME_TOWN).execute();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }

        var items = response.getItems();
        if (items.isEmpty()){
            return null;
        }
        var user = items.getFirst();

        var hometown = user.getHomeTown();
        if (hometown != null && !hometown.isEmpty()){
            return hometown;
        }

        var city = user.getCity();
        if (city != null && !city.getTitle().isEmpty()){
            return city.getTitle();
        }

        return null;
    }
}
