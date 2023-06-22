package edu.co.icesi.security;

import edu.co.icesi.model.User;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.json.JSONObject;

public class AdminRequest {

    private final static String ADMIN_USERNAME = "admin";

    private final static String ADMIN_PASSWORD = "admin";

    @Property(name = "micronaut.security.oauth2.clients.keycloak.grant-type")
    private String grandType;

    @Property(name = "micronaut.security.oauth2.clients.keycloak.realm-name")
    private String realmName;

    @Client("http://${micronaut.security.oauth2.clients.keycloak.keycloak-host}:${micronaut.security.oauth2.clients.keycloak.keycloak-port}")
    @Inject
    private HttpClient client;

    private String getAdminToken() {
        String requestBody = "username=" + ADMIN_USERNAME + "&password=" + ADMIN_PASSWORD
                + "&grant_type=" + grandType + "&client_id=admin-cli";

        HttpRequest<String> request = HttpRequest.POST("/realms/master/protocol/openid-connect/token", requestBody).
                contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON);

        Flowable<String> response = Flowable.fromPublisher(client.retrieve(request));
        String res = response.first("error").blockingGet();
        //HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        client.refresh();
        JSONObject jsonObject = new JSONObject(res);
        System.out.println("hpta bien");
        return jsonObject.get("access_token").toString();
    }

    public boolean registerUserKeycloak(User user){
        String token = getAdminToken();
        String bearerToken = "Bearer " + token;
        System.out.println("si1");
        String requestBody = String.format("{\"username\":\"%s\",\"email\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"," +
                "\"attributes\":{\"organization\":\"%s\"},\"credentials\":[{\"value\":\"password\"," +
                "\"temporary\":\"false\"}],\"enabled\":\"true\"}",user.getUsername(),user.getEmail(),user.getFirstname(),
                user.getLastname(),user.getOrganizationName());

        System.out.println("si2");
        HttpRequest<String> request = HttpRequest.POST("/admin/realms/"+realmName+"/users", requestBody).
                header("Authorization",bearerToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON);

        try{
            System.out.println("si3");
            Flowable<String> response = Flowable.fromPublisher(client.retrieve(request));
            System.out.println("si4");
            String res = response.first("error").blockingGet();
            System.out.println(res);
        }catch (HttpClientResponseException hcre){
            hcre.getMessage().contains("Conflict");
            //TODO waiting for exception to users conflict
        }
        return true;
    }

}
