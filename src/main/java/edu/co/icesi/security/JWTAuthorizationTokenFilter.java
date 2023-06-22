package edu.co.icesi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.co.icesi.constant.CodesError;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.Order;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.*;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Singleton
@Order(1)
@Filter("/**")
public class JWTAuthorizationTokenFilter implements HttpServerFilter {

    private static final String[] excludedPaths = {"POST /login", "POST /users"};

    private final static String TOKEN_PREFIX = "Bearer ";

    @Property(name = "micronaut.security.oauth2.clients.keycloak.client-id")
    private String clientId;

    @Property(name = "micronaut.security.oauth2.clients.keycloak.client-secret")
    private String clientSecret;

    @Property(name = "micronaut.security.oauth2.clients.keycloak.realm-name")
    private String realmName;

    @Client("http://${micronaut.security.oauth2.clients.keycloak.keycloak-host}:${micronaut.security.oauth2.clients.keycloak.keycloak-port}")
    @Inject
    private HttpClient client;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        System.out.println(request.toString());
        if(!excludePaths(request)){
            try{
                if(containsToken(request)) {
                    String jwt = request.getHeaders().getAuthorization().get().replace(TOKEN_PREFIX, StringUtils.EMPTY_STRING);
                    String token = decodeJWT(jwt);
                    //KeycloakUser keycloakUser = parseKeycloakUser(token);
                    //UserContextHolder.setUserContext(keycloakUser);
                    return chain.proceed(request);
                }else{
                    return createUnauthorizedFilter(new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.CODES_01.getCode(), CodesError.CODES_01.getMessage())));
                }
            } catch(JSONException e){
                return createUnauthorizedFilter(new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.CODES_01.getCode(), CodesError.CODES_01.getMessage())));
            } finally {
                UserContextHolder.clearContext();
            }
        }
        try{
            return chain.proceed(request);
        }catch (HttpClientResponseException e) {
            return createUnauthorizedFilter(new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.USER_NOT_REGISTER.getCode(), CodesError.USER_NOT_REGISTER.getMessage())));
        }
    }

    public boolean excludePaths(HttpRequest<?> request){
        if(request.getMethod().toString().equalsIgnoreCase("OPTIONS")){
            return true;
        }
        return Arrays.stream(excludedPaths).anyMatch(path -> path.equalsIgnoreCase(request.toString()));
    }

    @SneakyThrows
    private Publisher<MutableHttpResponse<?>> createUnauthorizedFilter(VarxenPerformanceException varxenPerformanceException) {
        ObjectMapper objectMapper = new ObjectMapper();

        VarxenPerformanceError varxenPerformanceError = varxenPerformanceException.getError();

        String message = objectMapper.writeValueAsString(varxenPerformanceError);

        MutableHttpResponse<?> response = HttpResponse.status(HttpStatus.UNAUTHORIZED).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).body(message);

        return Flowable.just(response);
    }

    private KeycloakUser parseKeycloakUser(String token){
        String requestBody = "client_id=" + clientId + "&client_secret=" + clientSecret
                + "&token=" + token;

        HttpRequest<String> request = HttpRequest.POST("/realms/"+realmName+"/protocol/openid-connect/token/introspect", requestBody).
                contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE).
                accept(MediaType.APPLICATION_JSON);

        Flowable<String> response = Flowable.fromPublisher(client.retrieve(request));
        String res = response.first("error").blockingGet();
        //HttpResponse<String> response = client.toBlocking().exchange(request, String.class);
        JSONObject jsonObject = new JSONObject(res);
        String email = jsonObject.get("email").toString();
        String username = jsonObject.get("preferred_username").toString();
        List<String> roles = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("roles");
        for (Object element:jsonArray) {
            roles.add(element.toString());
        }
        return new KeycloakUser(email, username, roles, token);
    }

    private String decodeJWT(String jwt){
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonObject = new JSONObject(payload);
        return jsonObject.get("openIdToken").toString();
    }

    private boolean containsToken(HttpRequest<?> request){
        HttpHeaders httpHeaders = request.getHeaders();
        if(httpHeaders.getAuthorization().isPresent()){
            String authHeader = httpHeaders.getAuthorization().get();
            return authHeader.startsWith(TOKEN_PREFIX);
        }else{
            return false;
        }
    }

}
