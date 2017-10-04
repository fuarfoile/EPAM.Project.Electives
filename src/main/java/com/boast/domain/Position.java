package com.boast.domain;

import java.util.Locale;
import java.util.ResourceBundle;

/** Перечисление возможных позиций пользователей*/
public enum Position {
    ADMIN("position.admin"),
    TEACHER("position.teacher"),
    STUDENT("position.student");

    private String position;

    private Position(String position) {
        this.position = position;
    }

    public String getPosition(String language) {
        Locale locale = new Locale(language);
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        return resource.getString(position);
    }
}
