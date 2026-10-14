package com.hrms;

import com.hrms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class EmployeeManager {

    public void menu(Scanner sc, int orgId) {
        while (true) {
            System.out.println();
            System.out.println("1. Add employee");
            System.out.println("2. View employees");
            System.out.println("3. Update employee");
            System.out.println("4. Delete employee");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": add(sc, orgId); break;
                case "2": view(orgId); break;
                case "3": update(sc, orgId); break;
                case "4": delete(sc, orgId); break;
                case "5": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void add(Scanner sc, int orgId) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();

        String sql = "INSERT INTO employee (org_id, name, email, phone, joined_on) "
                + "VALUES (?, ?, ?, ?, CURDATE())";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orgId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.executeUpdate();
            System.out.println("Employee added.");
        } catch (Exception e) {
            System.out.println("Could not add: " + e.getMessage());
        }
    }

    private void view(int orgId) {
        String sql = "SELECT emp_id, name, email, phone, joined_on FROM employee WHERE org_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orgId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean any = false;
                while (rs.next()) {
                    any = true;
                    System.out.println(rs.getInt("emp_id") + " | " + rs.getString("name")
                            + " | " + rs.getString("email") + " | " + rs.getString("phone")
                            + " | " + rs.getDate("joined_on"));
                }
                if (!any) {
                    System.out.println("No employees yet.");
                }
            }
        } catch (Exception e) {
            System.out.println("Could not view: " + e.getMessage());
        }
    }

    private void update(Scanner sc, int orgId) {
        System.out.print("Employee id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New name: ");
        String name = sc.nextLine();
        System.out.print("New email: ");
        String email = sc.nextLine();
        System.out.print("New phone: ");
        String phone = sc.nextLine();

        String sql = "UPDATE employee SET name = ?, email = ?, phone = ? "
                + "WHERE emp_id = ? AND org_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);
            ps.setInt(5, orgId);
            int rows = ps.executeUpdate();
            System.out.println(rows == 0 ? "No such employee." : "Employee updated.");
        } catch (Exception e) {
            System.out.println("Could not update: " + e.getMessage());
        }
    }

    private void delete(Scanner sc, int orgId) {
        System.out.print("Employee id to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM employee WHERE emp_id = ? AND org_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, orgId);
            int rows = ps.executeUpdate();
            System.out.println(rows == 0 ? "No such employee." : "Employee deleted.");
        } catch (Exception e) {
            System.out.println("Could not delete: " + e.getMessage());
        }
    }
}