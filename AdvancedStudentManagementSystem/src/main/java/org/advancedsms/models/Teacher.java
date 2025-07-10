package org.advancedsms.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Teacher extends Person {

//    private static final AtomicInteger counter = new AtomicInteger(1);
    private int teacherId;
    private String subject;

    private List<Course> courses = new ArrayList<>();

    public Teacher(){}

    public Teacher(String name, String email, int age, String subject) {
        super(name, email, age);
        this.subject    = subject;
//        this.teacherId  = counter.getAndIncrement();
    }

    public void addCourse(Course course) {
        // Teachers can teach up to 3 courses only
        if (courses.size() == 3){
            System.out.println("Cannot teach another course! Maximum teachable courses reached.");
        }
        else courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", subject='" + subject + '\'' +
                ", courses=" + courses +
                '}';
    }
}
