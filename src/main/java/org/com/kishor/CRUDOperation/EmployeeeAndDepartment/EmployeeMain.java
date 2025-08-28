package org.com.kishor.CRUDOperation.EmployeeeAndDepartment;

import java.util.Scanner;


public class EmployeeMain {
    public static void main(String[] args) {

        EmployeeCRUDMenu jdbc = new EmployeeCRUDMenu();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Insert Department");
            System.out.println("2. Insert Employee");
            System.out.println("3. Update Employee Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. View All Employees");
            System.out.println("6. Fetch Employees by Department");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Dept ID: ");
                    String deptId = sc.nextLine();
                    System.out.print("Enter Dept Name: ");
                    String deptName = sc.nextLine();
                    jdbc.insertDepartment(deptId, deptName);
                    break;

                case 2:
                    System.out.print("Enter Emp ID: ");
                    String empId = sc.nextLine();
                    System.out.print("Enter Emp Name: ");
                    String empName = sc.nextLine();
                    System.out.print("Enter Dept ID: ");
                    String empDeptId = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    sc.nextLine(); // consume newline
                    jdbc.insertEmployee(empId, empName, empDeptId, salary);
                    break;

                case 3:
                    System.out.print("Enter Emp ID to update salary: ");
                    String updateId = sc.nextLine();
                    System.out.print("Enter new Salary: ");
                    double newSalary = sc.nextDouble();
                    sc.nextLine(); // consume newline
                    jdbc.updateEmployeeSalary(updateId, newSalary);
                    break;

                case 4:
                    System.out.print("Enter Emp ID to delete: ");
                    String delId = sc.nextLine();
                    jdbc.deleteEmployee(delId);
                    break;

                case 5:
                    jdbc.viewAllEmployees();
                    break;

                case 6:
                    System.out.print("Enter Department Name: ");
                    String deptSearch = sc.nextLine();
                    jdbc.fetchEmployeesByDepartment(deptSearch);
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 0);

        sc.close();
    }

}
