package org.advancedsms;


import org.advancedsms.data.*;
import org.advancedsms.models.Student;
import org.advancedsms.models.Teacher;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws DataException {

        System.out.println("Hello and welcome!");

        //Dependencies needed for all demo operations
        FileIOTemplate<Student> studentFileIOTemplate = new FileIOTemplate<>();
        FileIOTemplate<Teacher> teacherFileIOTemplate = new FileIOTemplate<>();

        StudentJDBCTemplate studentJDBCTemplate = new StudentJDBCTemplate();
        TeacherJDBCTemplate teacherJDBCTemplate = new TeacherJDBCTemplate();

        StudentStreamsAPI streamsAPI = new StudentStreamsAPI();


        /*
            CRUD operations to H2 Database for Students working with Java I/O implementation
         */

        // 1. Functionality to read from a file and populate H2 database
        System.out.println("\n==========Imported Student Data===========");
        //read student data from file
        List<Student> importedStudents = studentFileIOTemplate.importStudentsFile("studentsImportFile.txt");
        importedStudents.forEach(System.out::println);

        //populate H2 database with student data
        studentJDBCTemplate.dbSetup(); // call set up to create table
        System.out.println("\n===========Populating H2 Database============");
        importedStudents.forEach(studentJDBCTemplate::add);

        //retrieve some data to show population is done
        System.out.println("\n==========All Students currently in H2 Database==========");
        studentJDBCTemplate.findAll().forEach(System.out::println);
        System.out.println("===========Data population done!=============");

        //ADD Operation
        System.out.println("\n============Add Operation===========");
        Student newStudent = new Student("Test Student", "test@mail.com",25,2);
        System.out.println("Before: " + newStudent);
        newStudent = studentJDBCTemplate.add(newStudent);
        System.out.println("After: " + newStudent);

        //UPDATE Operation
        System.out.println("\n============Update Operation==========");
        Student updateStudent = studentJDBCTemplate.findById(1);
        System.out.println("Before Update: " + updateStudent);
        updateStudent.setAge(31);
        updateStudent.setEmail("sadatUpdated@email.com");
        studentJDBCTemplate.update(updateStudent);
        updateStudent = studentJDBCTemplate.findById(1);
        System.out.println("After Update: " + updateStudent);

        //DELETE Operation
        System.out.println("\n=============Delete Operation============");
        System.out.println("Before delete: ");
        studentJDBCTemplate.findAll().forEach(System.out::println);
        studentJDBCTemplate.delete(3);
        System.out.println("\nAfter delete: deleted James Taylor");
        studentJDBCTemplate.findAll().forEach(System.out::println);

        //FindById Operation
        System.out.println("\n=========Find Student By ID Operation============");
        Student student = studentJDBCTemplate.findById(2);
        System.out.println("Student retrieved from database by ID(2): " + student);

        // Export Student data to file
        System.out.println("\n===========Operation to Export Student Data to file==========");
        studentFileIOTemplate.export("students", studentJDBCTemplate.findAll());
        System.out.println("Export operation completed! ");


        // 2. Streams API Operations
        // Filter students by selected grade level
        System.out.println("\n==========Display Students in same grade=========");
        System.out.println("Students in grade level 3[Junior]: ");
        streamsAPI.displayStudents(3,studentJDBCTemplate.findAll());

        //Count Operation
        System.out.println("\n=========Count Students in same grade===========");
        streamsAPI.count(4, studentJDBCTemplate.findAll());
        streamsAPI.count(3, studentJDBCTemplate.findAll());
        streamsAPI.count(2, studentJDBCTemplate.findAll());

        //Sort Operation
        System.out.println("\n==========Sort Students Alphabetically=========");
        streamsAPI.sort(studentJDBCTemplate.findAll());


        // 3. Multi Threading
        //Fetch and display students using 2 threads
        System.out.println("\n===========Display Students with Multithreading===========");

        //will utilize executor service to create 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (Student stu : studentJDBCTemplate.findAll()){
            executor.submit(new StudentsThread(stu));
        }

        executor.shutdown();

        try {
            //make sure all tasks/threads are complete before shutting down
            boolean completed = executor.awaitTermination(1, TimeUnit.MINUTES);
            if (completed) System.out.println("All threads completed operations!");
            else System.out.println("Threads did not complete operation");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}