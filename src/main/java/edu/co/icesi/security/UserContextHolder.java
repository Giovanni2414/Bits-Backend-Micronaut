package edu.co.icesi.security;

import jakarta.inject.Singleton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Singleton
public abstract class UserContextHolder {

    private static final ThreadLocal<KeycloakUser> userContextHolder = new ThreadLocal<>();

    public static void clearContext() {
        userContextHolder.remove();
    }

    /**
     * Get the actual context, if it does not exist. create an empty one
     *
     * @return {@link KeycloakUser}
     */
    public static KeycloakUser getContext() {
        System.out.println(userContextHolder);
        KeycloakUser ctx = userContextHolder.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            userContextHolder.set(ctx);
        }
        return ctx;
    }

    /**
     * Given a {@link KeycloakUser context} update the actual context
     *
     * @param context a {@link KeycloakUser} instance
     */
    public static void setUserContext(KeycloakUser context) {
        if (context != null) {
            userContextHolder.set(context);
        }
    }

    public static KeycloakUser createEmptyContext() {
        return new KeycloakUser();
    }

}
