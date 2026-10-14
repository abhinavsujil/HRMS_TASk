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

            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Please type a number.");
                continue;
            }

            if (choice == 1) {
                add(sc, orgId);
            } else if (choice == 2) {
                view(orgId);
            } else if (choice == 3) {
                update(sc, orgId);
            } else if (choice == 4) {
                delete(sc, orgId);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Wrong choice.");
            }
        }
    }

    public void add(Scanner sc, int orgId) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO employee (org_id, name, email, phone, joined_on) VALUES (?, ?, ?, ?, CURDATE())");
            ps.setInt(1, orgId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.executeUpdate();
            System.out.println("Employee added.");
            con.close();
        } catch (Exception e) {
            System.out.println("Could not add employee.");
        }
    }

    public void view(int orgId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM employee WHERE org_id = ?");
            ps.setInt(1, orgId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("emp_id") + " | "
                        + rs.getString("name") + " | "
                        + rs.getString("email") + " | "
                        + rs.getString("phone") + " | "
                        + rs.getString("joined_on"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not show employees.");
        }
    }

    public void update(Scanner sc, int orgId) {
        System.out.print("Employee id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New name: ");
        String name = sc.nextLine();
        System.out.print("New email: ");
        String email = sc.nextLine();
        System.out.print("New phone: ");
        String phone = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE employee SET name = ?, email = ?, phone = ? WHERE emp_id = ? AND org_id = ?");
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);
            ps.setInt(5, orgId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No such employee.");
            } else {
                System.out.println("Employee updated.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not update.");
        }
    }

    public void delete(Scanner sc, int orgId) {
        System.out.print("Employee id to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM employee WHERE emp_id = ? AND org_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, orgId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No such employee.");
            } else {
                System.out.println("Employee deleted.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not delete.");
        }
    }
}