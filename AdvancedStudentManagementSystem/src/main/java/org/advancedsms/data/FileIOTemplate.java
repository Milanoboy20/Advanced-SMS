package org.advancedsms.data;

import org.advancedsms.models.Student;
import org.advancedsms.models.Teacher;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileIOTemplate<T> {

    private T type;

    private static final String STUDENT_HEADER = "ID,Name,Email,Age,Grade";
    private static final String TEACHER_HEADER = "ID,Name,Email,Age,Subject";
    private static final File studentFile = new File("students.txt");

    private static final File teacherFile = new File("teachers.txt");

    public static void main(String[] args) {
//        try {
//            if (studentFile.createNewFile()) {
//                System.out.println("Created ne file: " + studentFile.getName());
//            } else {
//                System.out.println("file already exist!");
//            }
//        } catch (IOException e) {
//            throw  new RuntimeException(e.getMessage());
//        }

        try {
            List<Student> students = new ArrayList<>();
            Student student = new Student("Abdul Samad", "sadat@yahoo.com", 30, 4);
            student.setStudentId(1);
            Student student2 = new Student("Mike Hunt", "mike@yahoo.com", 25, 3);
            student2.setStudentId(2);

            students.add(student);
            students.add(student2);

            FileIOTemplate<Student> template = new FileIOTemplate<>();
            template.export("students", students);


            //import students file
            List<Student> importedStudents = template.importStudentsFile("studentsImportTest.txt");
            importedStudents.forEach(System.out::println);
            System.out.println("===========");

            StudentStreamsAPI streamsAPI = new StudentStreamsAPI();
            //display students in grade 3
            streamsAPI.displayStudents(4, importedStudents);

            System.out.println("===========Sorted Students List==========");
            streamsAPI.sort(importedStudents).forEach(System.out::println);

            System.out.println("=========Count number of students in grade level 3============");
            streamsAPI.count(3, importedStudents);

        } catch (DataException e) {
            e.printStackTrace();
        }
    }


    public void export(String type, List<T> data) throws DataException {
        try (PrintWriter writer = new PrintWriter(type + ".txt")){
            if (type.equals("students")) {
                writer.println(STUDENT_HEADER);

                for (T student : data) writer.println(serialize((Student) student));
            }
            else if (type.equals("teachers")){
                writer.println(TEACHER_HEADER);

                for (T teacher : data) writer.println(serialize((Teacher) teacher));
            }
            else {
                System.out.println("[Error]: You can only export students and teachers data!");
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex.getMessage());
        }
    }


    public List<Student> importStudentsFile(String filename) throws DataException {
        List<Student> students = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();//read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);

                if (fields.length == 4) {
                    students.add(deserializeS(fields));
                }
            }

        } catch (IOException e) {
            throw new DataException(e.getMessage());
        }

        return students;
    }

    public List<Teacher> importTeachersFile(String filename) throws DataException {
        List<Teacher> teachers = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();//read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);

                if (fields.length == 4) {
                    teachers.add(deserializeT(fields));
                }
            }

        } catch (IOException e) {
            throw new DataException(e.getMessage());
        }

        return teachers;
    }


    //helper methods
    private String serialize(Student student) {
        return String.format("%s,%s,%s,%s,%s",
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getGrade());
    }

    private String serialize(Teacher teacher) {
        return String.format("%s,%s,%s,%s,%s",
                teacher.getTeacherId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getAge(),
                teacher.getSubject());
    }

    private Student deserializeS(String[] fields) {
        Student student = new Student();
        student.setName(fields[0]);
        student.setEmail(fields[1]);
        student.setAge(Integer.parseInt(fields[2]));
        student.setGrade(Integer.parseInt(fields[3]));

        return student;
    }

    private Teacher deserializeT(String[] fields) {
        Teacher teacher = new Teacher();
        teacher.setName(fields[0]);
        teacher.setEmail(fields[1]);
        teacher.setAge(Integer.parseInt(fields[2]));
        teacher.setSubject(fields[3]);

        return teacher;
    }
}
