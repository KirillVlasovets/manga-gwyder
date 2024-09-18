package com.gorstreller.mangagwyder.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public final class SecurityUtils {

    public static String getNameOfAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static boolean isUserLoggedIn() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
               && !(authentication instanceof AnonymousAuthenticationToken)
               && authentication.isAuthenticated();
    }
}
