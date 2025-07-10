package org.advancedsms;

import org.advancedsms.data.DatabaseConnection;
import org.advancedsms.models.Student;

import java.sql.*;


public class JDBCTemplate {
    public static void main(String[] args) {

        try {

            DatabaseConnection dbConnection = DatabaseConnection.getInstance();
            Connection connection = dbConnection.getConnection();
            //once connection established, create statements
            Statement stmt = connection.createStatement();

            //create student table
            String createTable = "CREATE TABLE student (id int primary key auto_increment, name varchar(50), email varchar(255), age int, grade int);";
            stmt.executeUpdate(createTable);

            Student student = new Student("Abdul Samad", "sadatabdul@yahoo.com", 29, 4);
            Student student2 = new Student("Mallam Samad", "mallam@edu.com", 22, 2);

            //statement to insert student data into database
            String insertStudent = "insert into student (name, email, age, grade) values " +
                    "(?,?,?,?);";

            //prepared statement to insert object data for previous query
            PreparedStatement ps = connection.prepareStatement(insertStudent);

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setInt(4, student.getGrade());

            //execute statement
            ps.executeUpdate();

            ps.setString(1, student2.getName());
            ps.setString(2, student2.getEmail());
            ps.setInt(3, student2.getAge());
            ps.setInt(4, student2.getGrade());

            //execute statement
            ps.executeUpdate();



            //retrieve data from database
            String query = "select * from student";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int age = rs.getInt("age");
                int grade = rs.getInt("grade");

                System.out.println("ID: " + id + " \nName: " + name + " \nEmail: " + email + " \nAge: " + age + " \nGrade: " + grade);
                System.out.println("\n");
            }

            dbConnection.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
