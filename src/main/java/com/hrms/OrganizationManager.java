package com.hrms;

import com.hrms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class OrganizationManager {

    public void create(Scanner sc) {
        System.out.print("Organization name: ");
        String name = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("PAN: ");
        String pan = sc.nextLine();
        System.out.print("GST: ");
        String gst = sc.nextLine();
        System.out.print("Department: ");
        String department = sc.nextLine();
        System.out.print("Email (used for login): ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Number of employees: ");
        int count = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO organization "
                + "(org_name, address, pan, gst, department, email, password, employee_count) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, pan);
            ps.setString(4, gst);
            ps.setString(5, department);
            ps.setString(6, email);
            ps.setString(7, password);
            ps.setInt(8, count);

            ps.executeUpdate();
            System.out.println("Organization created.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("This email, PAN or GST is already registered.");
        } catch (Exception e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }
}
