package org.advancedsms.data;

import org.advancedsms.models.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentStreamsAPI {

    //display all students in the same grade
    public void displayStudents(int grade, List<Student> students) {
        students.stream().filter(s -> s.getGrade() == grade).forEach(System.out::println);
    }

    //count students in the same grade
    public void count(int grade, List<Student> students) {
        long count = students.stream().filter(s -> s.getGrade() == grade).count();
        System.out.println("There are " + count + " students in grade level " + grade);
    }

    //sort students alphabetically
    public List<Student> sort(List<Student> students) {
        return students.stream().sorted(Comparator.comparing(Student::getName)).collect(Collectors.toList());
    }
}
