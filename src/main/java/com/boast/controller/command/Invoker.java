package com.boast.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Контейнер команд*/
public class Invoker {
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    /**
     * Вызывает команду и возвращает полученый адрес страницы для перехода
     *
     * @param request Запрос клиента
     * @param response Ответ сервера
     * @return Адрес страницы для перехода
     */
    public String invokeCommand(HttpServletRequest request,
                                HttpServletResponse response) {
        return command.execute(request, response);
    }
}
