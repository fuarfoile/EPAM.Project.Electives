package com.boast.domain;

import java.util.Locale;
import java.util.ResourceBundle;

/** Перечисление возможных состояний курса*/
public enum CourseStatus {
    DEVELOPING("course.status.dev"),
    REGISTRATION("course.status.reg"),
    RUNNING("course.status.run"),
    FINISHED("course.status.end");

    private String courseStatus;

    private CourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseStatus(String language) {
        Locale locale = new Locale(language);
        ResourceBundle resource = ResourceBundle.getBundle("localization/translation", locale);

        return resource.getString(courseStatus);
    }
}
