package com.boast.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.boast.transferobject.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {
    @ResponseBody
    @RequestMapping(path = "/")
    public String home(HttpServletRequest request) {

        String contextPath = request.getContextPath();
        String host = request.getServerName();

        // Spring Boot >= 2.0.0.M7
        String endpointBasePath = "/actuator";

        StringBuilder sb = new StringBuilder();

        sb.append("<h2>Sprig Boot Actuator</h2>");
        sb.append("<ul>");

        // http://localhost:8090/actuator
        String url = "http://" + host + ":8090" + contextPath + endpointBasePath;

        sb.append("<li><a href='" + url + "'>" + url + "</a></li>");

        sb.append("</ul>");

        return sb.toString();
    }

    @ResponseBody
    @RequestMapping(path = "/test")
    public ResponseEntity<User> test(@Valid @RequestBody User user) {

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
