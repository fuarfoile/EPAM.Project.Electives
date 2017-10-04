package com.boast.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Интерфейс для реализации шаблонна комманды*/
public interface Command {

    /**
     * Выполняет метод команды и возвращает адрес страницы для перехода
     *
     * @param request Запрос клиента
     * @param response Ответ сервера
     * @return Адрес страницы для перехода
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
