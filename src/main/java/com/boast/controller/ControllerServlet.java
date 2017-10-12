package com.boast.controller;

import com.boast.controller.command.*;
import com.boast.model.dao.connection.impl.MySqlConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Реализация сервлета. Принимает все запросы клиента, передает на обработку командам и выдает ответ*/
@WebServlet(name = "MainServlet", urlPatterns = {"/controller"})
public class ControllerServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(ControllerServlet.class);

    public ControllerServlet() {
        super();
    }

    public void destroy() {
        super.destroy();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        performTask(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        performTask(request, response);
    }

    /**
     * Управляет всеми запросами от клиента выполняя заданные команды определенные в запросе
     *
     * @param request Запрос клиента
     * @param response Ответ сервера
     * @throws IOException
     * @throws ServletException
     */
    private void performTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TypeCommand typeCommand = TypeCommand.LOGIN;

        if (request.getParameter("TypeCommand") != null) {
            typeCommand = TypeCommand.valueOf(request.getParameter("TypeCommand"));
        }

        Client client = new Client();
        Command command = client.initCommand(typeCommand);
        Invoker invoker = new Invoker(command);
        String path = invoker.invokeCommand(request, response);

        MySqlConnectionFactory.getInstance().closeConnection();

        logger.info("performTask: " + typeCommand + ", result path = " + path);
        getServletConfig().getServletContext().getRequestDispatcher(path)
                .forward(request, response);

    }
}
