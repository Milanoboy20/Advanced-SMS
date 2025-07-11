package org.advancedsms.data;

import org.advancedsms.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentJDBCTemplate {

    private final DatabaseConnection dbConnector = DatabaseConnection.getInstance();

    private final Connection connection = dbConnector.getConnection();

    private Statement stmt;

    //database setup, create tables needed for operations
    public void dbSetup() {
        //create student table sql
        String createStudentTable = "CREATE TABLE student (id int primary key auto_increment, name varchar(50), email varchar(255), age int, grade int);";

        try {
            stmt = connection.createStatement();
            stmt.executeUpdate(createStudentTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Student> findAll() {

        final String sql = "select * from student;";
        List<Student> students = new ArrayList<>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getInt("grade"));

                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }


    public Student findById(int studentId) {
        final String sql = "select * from student where id = ?;";
        Student student = new Student();

        try {
            stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,studentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                student.setStudentId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setAge(rs.getInt("age"));
                student.setGrade(rs.getInt("grade"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public Student add(Student student) {
        final String sql = "insert into student (name, email, age, grade) values " +
                "(?,?,?,?);";

        //prepare statement
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setInt(4, student.getGrade());

            ps.executeUpdate();

            //get generated key and set created student id
            ResultSet rs = ps.getGeneratedKeys();
            int studentId = 0;
            while (rs.next()) {
                studentId = rs.getInt(1);
            }

            student.setStudentId(studentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return student;
    }


    public boolean update(Student student) {

        final String sql = "update student set " +
                "name = ?, " +
                "email = ?, " +
                "age = ?, " +
                "grade = ? " +
                "where id = ?;";
        int rowsAffected = 0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setInt(4, student.getGrade());
            ps.setInt(5, student.getStudentId());

            rowsAffected = ps.executeUpdate();


        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }


    public boolean delete(int studentId) {
        final String sql = "delete from student where id = ?;";

        int rowsAffected = 0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, studentId);

            //execute delete
            rowsAffected = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }


    public void closeDbConnection() {
        dbConnector.closeConnection();
    }
}
