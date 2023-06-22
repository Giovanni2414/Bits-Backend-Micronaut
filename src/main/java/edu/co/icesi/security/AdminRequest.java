package edu.co.icesi.security;

import edu.co.icesi.model.User;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.inject.Inject;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
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
        System.out.println("Hpta1");
        HttpRequest<String> request = HttpRequest.POST("/realms/master/protocol/openid-connect/token", requestBody).
                contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON);

        System.out.println("Hpta2");
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        System.out.println("Hpta3");
        client.refresh();
        System.out.println("Hpta4");
        JSONObject jsonObject = new JSONObject(response.body());
        System.out.println("Hpta5");
        return jsonObject.get("access_token").toString();
    }

    public boolean registerUserKeycloak(User user){
        String token = getAdminToken();
        String bearerToken = "Bearer " + token;
        String requestBody = String.format("{\"username\":\"%s\",\"email\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"," +
                "\"attributes\":{\"organization\":\"%s\"},\"credentials\":[{\"value\":\"password\"," +
                "\"temporary\":\"false\"}],\"enabled\":\"true\"}",user.getUsername(),user.getEmail(),user.getFirstname(),
                user.getLastname(),user.getOrganizationName());

        HttpRequest<String> request = HttpRequest.POST("/admin/realms/"+realmName+"/users", requestBody).
                header("Authorization",bearerToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON);

        try{
            client.toBlocking().exchange(request, String.class);
        }catch (HttpClientResponseException hcre){
            hcre.getMessage().contains("Conflict");
            //TODO waiting for exception to users conflict
        }
        return true;
    }

}
