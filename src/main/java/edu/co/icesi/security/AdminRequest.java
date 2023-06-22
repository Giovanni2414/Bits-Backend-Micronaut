package edu.co.icesi.security;

import edu.co.icesi.model.User;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import org.json.JSONObject;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

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

    @Client("http://${micronaut.security.oauth2.clients.keycloak.keycloak-host}:${micronaut.security.oauth2.clients.keycloak.keycloak-port}")
    @Inject
    private HttpClient client2;

    private String getAdminToken() {
        String requestBody = "username=" + ADMIN_USERNAME + "&password=" + ADMIN_PASSWORD
                + "&grant_type=" + grandType + "&client_id=admin-cli";

        HttpRequest<String> request = HttpRequest.POST("/realms/master/protocol/openid-connect/token", requestBody).
                contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON);

        //Flowable<HttpResponse<String>> response = Flowable.fromPublisher(client.exchange(request, String.class));


        HttpResponse<String> res;
        try {
            res = Mono.from(client.exchange(request, String.class)).toFuture().get();
            String body = res.getBody().orElseThrow(()->new RuntimeException("Err"));
            JSONObject jsonObject = new JSONObject(body);
            return jsonObject.get("access_token").toString();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }finally {
            client.refresh();
        }
    }

    public boolean registerUserKeycloak(User user) {
        String token = getAdminToken();
        System.out.println(token);
        String bearerToken = "Bearer " + token;
        String requestBody = String.format("{\"username\":\"%s\",\"email\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"," +
                        "\"attributes\":{\"organization\":\"%s\"},\"credentials\":[{\"value\":\"password\"," +
                        "\"temporary\":\"false\"}],\"enabled\":\"true\"}", user.getUsername(), user.getEmail(), user.getFirstname(),
                user.getLastname(), user.getOrganizationName());

        HttpRequest<String> request = HttpRequest.POST("/admin/realms/" + realmName + "/users", requestBody).
                header("Authorization", bearerToken).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.APPLICATION_JSON);

        HttpResponse<String> res;
        try {
            client.refresh().exchange(request, String.class);
            //Mono.from(client.exchange(request, String.class)).toFuture().join();
            return true;
        } catch (HttpClientResponseException e) {
            throw new RuntimeException(e);
        }finally {
            client.refresh();
        }
    }

}
