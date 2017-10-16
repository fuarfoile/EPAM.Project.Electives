package com.boast.domain;

import com.boast.controller.exception.InvalidMarkException;
import com.boast.model.util.constant.Values;

/** Сущность данных студента на курсе*/
public class StudentCourse {

    private int id = -1;
    private int studentId;
    private int courseId;
    private int teacherId;
    private int mark;
    private String review;
    private CourseStatus status = CourseStatus.DEVELOPING;

    private String courseName;
    private String studentName;
    private String studentSurname;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return this.studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return this.courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getTeacherId() { return this.teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public int getMark() { return this.mark; }
    public void setMark(int mark) throws InvalidMarkException {
        if (mark >= Values.MIN_MARK && mark <= Values.MAX_MARK) {
            this.mark = mark;
        } else {
            throw new InvalidMarkException("Mark should be in next diapason: [" + Values.MIN_MARK + ", " + Values.MAX_MARK + "] ");
        }
    }

    public String getReview() { return this.review; }
    public void setReview(String review) {
        this.review = review;
    }

    public String getCourseName() { return this.courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getStudentName() { return this.studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentSurname() { return this.studentSurname; }
    public void setStudentSurname(String studentSurname) { this.studentSurname = studentSurname; }

    public CourseStatus getStatus() { return this.status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "[id = " + id +
                ", studentId = " + studentId +
                ", courseId = " + courseId +
                ", courseName = " + courseName +
                ", studentName = " + studentName +
                ", studentSurname = " + studentSurname +
                ", mark = " + mark +
                ", review = " + review +
                ", status = " + status +"]";
    }
}
