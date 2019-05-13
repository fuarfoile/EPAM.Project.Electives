package com.boast.controller;

import com.boast.controller.command.*;
import com.boast.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
public class TestController {

    private static Logger logger = LogManager.getLogger(TestController.class);

    @RequestMapping(path = "/")
    public String index(Model model) {

        logger.info("entering index.jsp");
        return "index";
    }

    @RequestMapping(path = "/controller")
    public String login(HttpServletRequest request, HttpServletResponse response) {

        TypeCommand typeCommand = TypeCommand.LOGIN;

        if (request.getParameter("TypeCommand") != null) {
            typeCommand = TypeCommand.valueOf(request.getParameter("TypeCommand"));
        }

        Client client = new Client();
        Command command = client.initCommand(typeCommand);
        Invoker invoker = new Invoker(command);
        String path = invoker.invokeCommand(request, response);

        path = path.substring(5, path.length()-4);

        logger.info("path = " + path);

        return path;
    }

    @ResponseBody
    @RequestMapping(path = "/test")
    public ResponseEntity<User> test(@Valid @RequestBody User user) {

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
