package com.boast.domain.builder.impl;

import com.boast.controller.exception.InvalidMarkException;
import com.boast.domain.Course;
import com.boast.domain.CourseStatus;
import com.boast.domain.builder.Builder;

public class CourseBuilder implements Builder<Course> {

    private Course course;

    public CourseBuilder() {
        course = new Course();
    }

    public CourseBuilder setId(int id){
        course.setId(id);
        return this;
    }

    public CourseBuilder setName(String name) {
        course.setName(name);
        return this;
    }

    public CourseBuilder setDescription(String description) throws InvalidMarkException {
        course.setDescription(description);
        return this;
    }

    public CourseBuilder setTeacherId(int teacherId) {
        course.setTeacherId(teacherId);
        return this;
    }

    public CourseBuilder setStatus(CourseStatus status) {
        course.setStatus(status);
        return this;
    }

    public CourseBuilder setMaxStudentsCount(int maxStudentsCount) {
        course.setMaxStudentsCount(maxStudentsCount);
        return this;
    }

    public CourseBuilder setStudentsCount(int studentsCount) {
        course.setStudentsCount(studentsCount);
        return this;
    }

    public CourseBuilder setTeacherName(String teacherName) {
        course.setTeacherName(teacherName);
        return this;
    }

    public CourseBuilder setTeacherSurname(String teacherSurname) {
        course.setTeacherSurname(teacherSurname);
        return this;
    }

    @Override
    public Course build() {
        return course;
    }
}
