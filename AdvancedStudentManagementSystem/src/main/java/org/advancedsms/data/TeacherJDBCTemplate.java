package org.advancedsms.data;

import org.advancedsms.models.Student;
import org.advancedsms.models.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherJDBCTemplate {

    private final DatabaseConnection dbConnector = DatabaseConnection.getInstance();

    private final Connection connection = dbConnector.getConnection();

    private Statement stmt;

    //database setup, create tables needed for operations
    public void dbSetup() {
        //create teacher table sql
        String createStudentTable = "CREATE TABLE teacher (id int primary key auto_increment, name varchar(50), email varchar(255), age int, subject varchar(50));";

        try {
            stmt.executeUpdate(createStudentTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Teacher> findAll() {

        final String sql = "select * from student;";
        List<Teacher> teachers = new ArrayList<>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setAge(rs.getInt("age"));
                teacher.setSubject(rs.getString("subject"));

                teachers.add(teacher);
            }

            //close db connection after operations
            dbConnector.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return teachers;
    }


    public Teacher findById(int teacherId) {
        final String sql = "select * from teacher where id = ?;";
        Teacher teacher = new Teacher();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                teacher.setTeacherId(rs.getInt("id"));
                teacher.setName(rs.getString("name"));
                teacher.setEmail(rs.getString("email"));
                teacher.setAge(rs.getInt("age"));
                teacher.setSubject(rs.getString("subject"));
            }

            dbConnector.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    public Teacher add(Teacher teacher) {
        final String sql = "insert into teacher (name, email, age, subject) values " +
                "(?,?,?,?);";

        //prepare statement
        try {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, teacher.getName());
            ps.setString(2, teacher.getEmail());
            ps.setInt(3, teacher.getAge());
            ps.setString(4, teacher.getSubject());

            ps.executeUpdate();

            //get generated key and set created teacher id
            ResultSet rs = ps.getGeneratedKeys();
            int teacherId = 0;
            while (rs.next()) {
                teacherId = rs.getInt(1);
            }

            teacher.setTeacherId(teacherId);

            //close bd connection
            dbConnector.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return teacher;
    }


    public boolean update(Teacher teacher) {

        final String sql = "update teacher set " +
                "name = ?, " +
                "email = ?, " +
                "age = ?, " +
                "subject = ? " +
                "where id = ?;";
        int rowsAffected = 0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, teacher.getName());
            ps.setString(2, teacher.getEmail());
            ps.setInt(3, teacher.getAge());
            ps.setString(4, teacher.getSubject());
            ps.setInt(5, teacher.getTeacherId());

            rowsAffected = ps.executeUpdate();

            dbConnector.closeConnection();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }


    public boolean delete(int teacherId) {
        final String sql = "delete from teacher where id = ?;";

        int rowsAffected = 0;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, teacherId);

            //execute delete
            rowsAffected = ps.executeUpdate();

            //close connection
            dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected > 0;
    }

}
