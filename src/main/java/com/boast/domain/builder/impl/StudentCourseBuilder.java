package com.boast.domain.builder.impl;

import com.boast.controller.exception.MarkException;
import com.boast.domain.CourseStatus;
import com.boast.domain.StudentCourse;
import com.boast.domain.builder.Builder;

public class StudentCourseBuilder implements Builder<StudentCourse> {

    private StudentCourse studentCourse;

    public StudentCourseBuilder() {
        studentCourse = new StudentCourse();
    }

    public StudentCourseBuilder setId(int id){
        studentCourse.setId(id);
        return this;
    }

    public StudentCourseBuilder setStudentId(int studentId) {
        studentCourse.setStudentId(studentId);
        return this;
    }

    public StudentCourseBuilder setCourseId(int courseId) {
        studentCourse.setCourseId(courseId);
        return this;
    }

    public StudentCourseBuilder setTeacherId(int teacherId) {
        studentCourse.setTeacherId(teacherId);
        return this;
    }

    public StudentCourseBuilder setMark(int mark) throws MarkException{
        studentCourse.setMark(mark);
        return this;
    }

    public StudentCourseBuilder setReview(String review) {
        studentCourse.setReview(review);
        return this;
    }

    public StudentCourseBuilder setCourseName(String courseName) {
        studentCourse.setCourseName(courseName);
        return this;
    }

    public StudentCourseBuilder setStudentName(String studentName) {
        studentCourse.setStudentName(studentName);
        return this;
    }

    public StudentCourseBuilder setStudentSurname(String studentSurname) {
        studentCourse.setStudentSurname(studentSurname);
        return this;
    }

    public StudentCourseBuilder setStatus(CourseStatus status) {
        studentCourse.setStatus(status);
        return this;
    }

    @Override
    public StudentCourse build() {
        return studentCourse;
    }
}
