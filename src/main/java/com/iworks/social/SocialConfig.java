package com.iworks.social;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;

import com.iworks.social.interceptor.CustomConnectInterceptor;

@Configuration
public class SocialConfig {

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        ConnectController controller = new ConnectController(connectionFactoryLocator, connectionRepository);

        // controller.setApplicationUrl(environment.getProperty("application.url");
        // controller.setApplicationUrl("http://www.uol.com.br");
        // connectController.setApplicationUrl("/hello");

        // http://www.scorgar.be/blog/development-how-to-simple-integration-of-a-facebook-login-with-spring-security-framework-2/
        // http://stackoverflow.com/questions/18019946/spring-social-1-0-3-connect-interceptor-ignored
        controller.addInterceptor(new CustomConnectInterceptor());

        return controller;
    }
}