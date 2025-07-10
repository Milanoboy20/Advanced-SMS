package org.advancedsms.models;

public class Person {
    private String name;
    private int age;
    private String email;

    public Person(String name, String email, int age) {
        this.name   = name;
        this.email  = email;
        this.age    = age;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            System.out.println("Age cannot be zero or negative!");
        }
        else this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
