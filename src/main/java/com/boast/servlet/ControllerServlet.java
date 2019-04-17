package com.boast.servlet;

import com.boast.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainServlet", urlPatterns = {"/controller"})
public class ControllerServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(ControllerServlet.class);

    public ControllerServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        performTask(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        performTask(request, response);
    }

    public void performTask(HttpServletRequest request, HttpServletResponse response) {

        Receiver receiver = new Receiver(request, response);
        Client client = new Client(receiver);
        TypeCommand typeCommand = TypeCommand.LOGIN;
        if (request.getParameter("TypeCommand") != null) {
            typeCommand = TypeCommand.valueOf(request.getParameter("TypeCommand"));
        }
        Command command = client.initCommand(typeCommand);
        Invoker invoker = new Invoker(command);
        String path = invoker.invokeCommand();

        try {
            logger.info("performTask: " + typeCommand + ", result path = " + path);
            getServletConfig().getServletContext().getRequestDispatcher(path)
                    .forward(request, response);
        } catch (ServletException | IOException e) {
            logger.error("performTask: " + e);
        }
    }
}
