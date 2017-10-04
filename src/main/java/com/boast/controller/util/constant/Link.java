package com.boast.controller.util.constant;

/** Перечисление доступных путей страниц*/
public enum Link {
    ERROR("/jsp/error.jsp"),
    WELCOME("/index.jsp"),
    LOGIN("/jsp/login.jsp"),
    SIGNUP("/jsp/signup.jsp"),
    PASS_RESET("/jsp/passreset.jsp"),
    FORGOT("/jsp/forgot.jsp"),
    PROFILE("/jsp/profile.jsp"),
    TEACHER_PROFILE("/jsp/teacher_profile.jsp"),
    ACCOUNT_UPDATE("/jsp/accountupdate.jsp"),
    COURSES("/jsp/courses.jsp"),
    COURSE("/jsp/course.jsp"),
    COURSE_CREATE("/jsp/coursecreate.jsp");

    private String link;

    private Link(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
