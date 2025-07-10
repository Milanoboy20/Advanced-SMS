package org.advancedsms.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Student extends Person {

    private static final AtomicInteger counter = new AtomicInteger(1);
    private int studentId, grade;

    private List<Course> courses = new ArrayList<>();

    public Student(){
    }

    public Student(String name, String email, int age, int grade) {
        super(name, email, age);
        this.grade      = grade;
        this.studentId  = counter.getAndIncrement();

    }

    public void addCourse(Course course) {
        // Student registered courses max is 5
        if (courses.size() == 5){
            System.out.println("Cannot add another course! Maximum course registrations reached.");
        }
        else courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public int getStudentId() {
        return studentId;
    }

    //No setStudentId, IDs are auto generated

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", grade=" + grade +
                ", courses=" + courses +
                '}';
    }
}
