package org.com.kishor.CRUDOperation.EmployeeeAndDepartment;

import java.sql.*;

import org.com.kishor.CRUDOperation.DBConnection;


public class EmployeeCRUDMenu {

    private Connection conn;

    public EmployeeCRUDMenu() {
        try {
            conn = DBConnection.getConnection();
            System.out.println("Connected to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert department
    public void insertDepartment(String deptId, String deptName) {
        String sql = "INSERT INTO Department(dept_id, dept_name) VALUES(?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deptId);
            ps.setString(2, deptName);
            ps.executeUpdate();
            System.out.println("Department inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert employee
    public void insertEmployee(String empId, String empName, String deptId, double salary) {
        String sql = "INSERT INTO Employee(emp_id, emp_name, dept_id, salary) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            ps.setString(2, empName);
            ps.setString(3, deptId);
            ps.setDouble(4, salary);
            ps.executeUpdate();
            System.out.println("Employee inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update employee salary
    public void updateEmployeeSalary(String empId, double newSalary) {
        String sql = "UPDATE Employee SET salary = ? WHERE emp_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newSalary);
            ps.setString(2, empId);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Salary updated successfully!");
            else
                System.out.println("Employee not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete employee
    public void deleteEmployee(String empId) {
        String sql = "DELETE FROM Employee WHERE emp_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Employee deleted successfully!");
            else
                System.out.println("Employee not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View all employees with department
    public void viewAllEmployees() {
        String sql = "SELECT e.emp_id, e.emp_name, e.salary, d.dept_name "
                + "FROM Employee e JOIN Department d ON e.dept_id = d.dept_id";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            System.out.println("EmpID | EmpName | Salary | Department");
            System.out.println("-------------------------------------");
            while (rs.next()) {
                System.out.printf("%s | %s | %.2f | %s%n", rs.getString("emp_id"), rs.getString("emp_name"),
                        rs.getDouble("salary"), rs.getString("dept_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch employees by department
    public void fetchEmployeesByDepartment(String deptName) {
        String sql = "SELECT e.emp_id, e.emp_name, e.salary "
                + "FROM Employee e JOIN Department d ON e.dept_id = d.dept_id " + "WHERE d.dept_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, deptName);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("EmpID | EmpName | Salary");
                System.out.println("------------------------");
                while (rs.next()) {
                    System.out.printf("%s | %s | %.2f%n", rs.getString("emp_id"), rs.getString("emp_name"),
                            rs.getDouble("salary"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
