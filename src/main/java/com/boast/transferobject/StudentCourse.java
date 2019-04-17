package com.boast.transferobject;

public class StudentCourse {

    private int id = -1;
    private int studentId;
    private int courseId;
    private int mark;
    private String review;

    private String courseName;
    private String studentName;
    private String studentSurname;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return this.studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return this.courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getMark() { return this.mark; }
    public void setMark(int mark) { this.mark = mark; }

    public String getReview() { return this.review; }
    public void setReview(String review) { this.review = review; }

    public String getCourseName() { return this.courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getStudentName() { return this.studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentSurname() { return this.studentSurname; }
    public void setStudentSurname(String studentSurname) { this.studentSurname = studentSurname; }

    @Override
    public String toString() {
        return "[studentId = " + studentId +
                ", courseId = " + courseId +
                ", courseName = " + courseName +
                ", studentName = " + studentName +
                ", studentSurname = " + studentSurname +
                ", mark = " + mark +
                ", review = " + review + "]";
    }
}
