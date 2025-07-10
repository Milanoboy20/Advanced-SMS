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
        try {
            if (studentFile.createNewFile()) {
                System.out.println("Created ne file: " + studentFile.getName());
            } else {
                System.out.println("file already exist!");
            }
        } catch (IOException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }


    public void export(String type, List<T> data) throws DataException {
        try (PrintWriter writer = new PrintWriter(type + ".txt")){
            if (type.equals("students")) {
                writer.println(STUDENT_HEADER);

                for (T student : data) writer.println(serialize((Student) student));
            }
            else {
                writer.println(TEACHER_HEADER);

                for (T teacher : data) writer.println(serialize((Teacher) teacher));
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
