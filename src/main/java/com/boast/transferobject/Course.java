package com.boast.transferobject;

public class Course {

    private int id;
    private String name = null;
    private int teacherId;
    private boolean deployed = false;

    private int maxStudentsCount;
    private int studentsCount;

    private String teacherName;
    private String teacherSurname;

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public int getTeacherId() { return this.teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }

    public boolean isDeployed() { return this.deployed; }
    public void setDeployed(boolean deployed) { this.deployed = deployed; }

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
                ", teacher_id= " + teacherId +
                ", teacherName= " + teacherName +
                ", teacherSurname= " + teacherSurname +
                ", deployed = " + deployed + "]";
    }
}
