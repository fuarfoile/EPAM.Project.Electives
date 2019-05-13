package com.boast.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** Сущность курса*/
@Entity
@Table(name = "course")
public class Course {

    @Id
    private int id = -1;
    private String name = null;
    private String description = null;
    private int teacherId = 0;
    private CourseStatus status = CourseStatus.DEVELOPING;

    private int maxStudentsCount;
    private int studentsCount;

    private String teacherName;
    private String teacherSurname;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public int getTeacherId() { return this.teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public CourseStatus getStatus() { return this.status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    public int getMaxStudentsCount() { return this.maxStudentsCount; }
    public void setMaxStudentsCount(int maxStudentsCount) { this.maxStudentsCount = maxStudentsCount; }

    public int getStudentsCount() { return this.studentsCount; }
    public void setStudentsCount(int studentsCount) { this.studentsCount = studentsCount; }

    public String getTeacherName() { return this.teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getTeacherSurname() { return this.teacherSurname; }
    public void setTeacherSurname(String teacherSurname) { this.teacherSurname = teacherSurname; }

    @Override
    public String toString() {
        return "[" + name +
                ", id = " + id +
                ", teacher_id = " + teacherId +
                ", teacherName = " + teacherName +
                ", teacherSurname = " + teacherSurname +
                ", maxStudentsCount = " + maxStudentsCount +
                ", status = " + status + "]";
    }
}
