package source;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

public class VkApi {
    private final UserActor actor;
    private final VkApiClient vk;

    public VkApi(){
        vk = new VkApiClient(new HttpTransportClient());
        try {
            var authResponse = vk.oAuth()
                    .userAuthorizationCodeFlow(Config.getAppId(), Config.getClientSecret(),
                            Config.getRedirectUri(), Config.getCode())
                    .execute();
            actor = new UserActor((long)authResponse.getUserId(), authResponse.getAccessToken());
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRegion(String studentName){
        return "";
    }
}
