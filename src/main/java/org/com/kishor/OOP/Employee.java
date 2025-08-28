package org.com.kishor.OOP;

public class Employee {

    private String name;
    private int id;
    protected double salary;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter methods (Encapsulation)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    // Method to be overridden
    public void calculateSalary() {
        salary = 0; // default, to be overridden
    }

    // Display details
    public void displayEmployeeDetails() {
        System.out.println("ID: " + id + ", Name: " + name + ", Salary: " + salary);
    }
}
