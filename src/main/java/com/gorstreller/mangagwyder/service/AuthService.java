package com.gorstreller.mangagwyder.service;

import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.WrappedSession;

public class AuthService {

    private static final String USER_KEY = "user";

    public static void login(String username) {
        getCurrentSession().setAttribute(USER_KEY, username);
    }

    public static void logout() {
        getCurrentSession().removeAttribute(USER_KEY);
    }

    public static boolean isUserLoggedIn() {
        return getCurrentUser() != null;
    }

    public static String getCurrentUser() {
        return (String) getCurrentSession().getAttribute(USER_KEY);
    }

    private static WrappedSession getCurrentSession() {
        return VaadinService.getCurrentRequest().getWrappedSession();
    }
}
