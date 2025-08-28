package org.com.kishor.OOP;

public class FullTimeEmployee extends Employee {

    private double monthlySalary;

    public FullTimeEmployee(int id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public void calculateSalary() {
        salary = monthlySalary; // full-time salary = monthly fixed salary
    }
}
