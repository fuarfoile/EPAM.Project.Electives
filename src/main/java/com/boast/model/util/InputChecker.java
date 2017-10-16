package com.boast.model.util;

import com.boast.domain.Course;
import com.boast.domain.StudentCourse;
import com.boast.domain.User;
import com.boast.model.util.constant.Values;

public class InputChecker {
    public static boolean check(User user) {

        try {
            if (user.getName().length() > Values.MAX_USER_NAME_LENGTH ||
                    user.getSurname().length() > Values.MAX_USER_SURNAME_LENGTH ||
                    user.getEmail().length() > Values.MAX_USER_EMAIL_LENGTH ||
                    (user.getPassword() != null && user.getPassword().length() > Values.MAX_USER_PASSWORD_LENGTH) ||
                    (user.getPhoneNumber() != null && user.getPhoneNumber().length() > Values.MAX_USER_PHONE_NUMBER_LENGTH)) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

        return true;
    }

    public static boolean check(Course course) {

        try {
            if (course.getName().length() > Values.MAX_COURSE_NAME_LENGTH ||
                    (course.getDescription() != null && course.getDescription().length() > Values.MAX_DESCRIPTION_LENGTH)) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

        return true;
    }

    public static boolean check(StudentCourse studentCourse) {

        try {
            if (studentCourse.getReview() != null && studentCourse.getReview().length() > Values.MAX_REVIEW_LENGTH) {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

        return true;
    }
}
