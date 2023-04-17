package edu.co.icesi.security;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Map;

@Named("keycloak")
@Singleton
@Slf4j
public class KeycloakUserDetailsMapper implements OauthAuthenticationMapper {

    @Property(name = "micronaut.security.oauth2.clients.keycloak.client-id")
    private String clientId;
    @Property(name = "micronaut.security.oauth2.clients.keycloak.client-secret")
    private String clientSecret;

    @Property(name = "micronaut.security.oauth2.clients.keycloak.realm-name")
    private String realmName;

    @Client("http://${micronaut.security.oauth2.clients.keycloak.keycloak-host}:${micronaut.security.oauth2.clients.keycloak.keycloak-port}")
    @Inject
    private RxHttpClient client;

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(TokenResponse tokenResponse, @Nullable State state) {
        Flowable<HttpResponse<KeycloakUser>> res = client
                .exchange(HttpRequest.POST("/realms/"+ realmName +"/protocol/openid-connect/token/introspect", "token=" + tokenResponse.getAccessToken())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .basicAuth(clientId, clientSecret), KeycloakUser.class);

        return res.map(user -> {
            Map<String, Object> attrs = new HashMap<>();
            attrs.put("openIdToken", tokenResponse.getAccessToken());
            return AuthenticationResponse.success(user.body().getUsername(), user.body().getRoles(), attrs);
        });
    }
}