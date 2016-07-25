package com.iworks.social.controller;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.channels.FileChannel;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SocialController {

    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    @Inject
    public SocialController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);

        if (connection == null) {
            return "redirect:/connect/facebook";
        }

        System.out.println("Access Token: " + connection.createData().getAccessToken());

        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        model.addAttribute("accessToken", connection.createData().getAccessToken());

        PagedList<Post> feed = facebook.feedOperations().getFeed();

        model.addAttribute("feed", feed);
        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String connectFacebook(Model model) {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);

        if (connection == null) {
            return "redirect:/connect/facebook";
        }

        System.out.println("Access Token: " + connection.createData().getAccessToken());

        model.addAttribute("facebookProfile", facebook.userOperations().getUserProfile());
        model.addAttribute("accessToken", connection.createData().getAccessToken());

        PagedList<Post> feed = facebook.feedOperations().getFeed();

        model.addAttribute("feed", feed);
        return "hello";
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView method(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);

        if (connection == null) {
            return new ModelAndView("redirect:/connect/facebook");
        }

        String accessToken = connection.createData().getAccessToken();

        System.out.println("id: " + facebook.userOperations().getUserProfile().getId());
        System.out.println("name: " + facebook.userOperations().getUserProfile().getName());
        System.out.println("email: " + facebook.userOperations().getUserProfile().getEmail());

        // connection.getKey();
        // request.getAttribute("scopedTarget.facebook");

        System.out.println("Access Token: " + accessToken);

        modelMap.put("access_token", accessToken);
        response.setHeader("access_token", accessToken);

        return new ModelAndView("redirect:" + "http://www.uol.com.br", modelMap);
    }
}
