package org.advancedsms.data;

import org.advancedsms.models.Student;

public class StudentsThread implements Runnable{
    private final Student student;

    public StudentsThread(Student student) {
        this.student = student;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + ": " + student);
    }

}
