package com.iworks.social.interceptor;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class CustomConnectInterceptor implements ConnectInterceptor<Facebook> {
    @Override
    public void preConnect(ConnectionFactory<Facebook> connectionFactory, MultiValueMap<String, String> multiValueMap, WebRequest webRequest) {
        System.out.println("PRE CONNECT!!!!!!!!!!!!!!!");
        webRequest.setAttribute("redirectUrl", "test.com", WebRequest.SCOPE_SESSION);
    }

    @Override
    public void postConnect(Connection<Facebook> connection, WebRequest webRequest) {
        UserProfile userProfile = connection.fetchUserProfile();

        System.out.println(connection.getDisplayName());
        System.out.println(userProfile.getEmail());
        System.out.println(connection.getProfileUrl());

        System.out.println("POST CONNECT!!!!!!!!!!!!!!!");
        String redirectUrl = (String) webRequest.getAttribute("redirectUrl", WebRequest.SCOPE_SESSION);
        if (redirectUrl != null) {
            System.out.println("REDIRECT URL: " + redirectUrl);
            webRequest.removeAttribute("redirectUrl", WebRequest.SCOPE_SESSION);
        }

        /*
        FacebookDTO dto = new FacebookDTO();
        dto.setDisplayName(connection.getDisplayName());
        dto.setEmail(userProfile.getEmail());
        dto.setId(extractId(connection.getProfileUrl()));
        dto.setImageURL(getImageUrl(connection, 285, 285));

        userTaskService.loginOrCreateFacebookUser(dto);
        */
    }
}
