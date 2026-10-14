package com.hrms;

import com.hrms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginManager {

    public void loginMenu(Scanner sc) {
        while (true) {
            System.out.println();
            System.out.println("1. Enter email and password");
            System.out.println("2. Forgot password");
            System.out.println("3. Back to main menu");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Please type a number.");
                continue;
            }

            if (choice == 1) {
                int orgId = login(sc);
                if (orgId != -1) {
                    new Dashboard().show(sc, orgId);
                    break;
                }
            } else if (choice == 2) {
                forgotPassword(sc);
            } else if (choice == 3) {
                break;
            } else if (choice == 4) {
                System.out.println("Bye.");
                System.exit(0);
            } else {
                System.out.println("Wrong choice.");
            }
        }
    }

    public int login(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        int orgId = -1;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT org_id, org_name FROM organization WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Welcome to " + rs.getString("org_name"));
                orgId = rs.getInt("org_id");
            } else {
                System.out.println("Wrong email or password.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Login failed.");
        }
        return orgId;
    }

    public void forgotPassword(Scanner sc) {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE organization SET password = ? WHERE email = ?");
            ps.setString(1, newPassword);
            ps.setString(2, email);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No organization found with this email.");
            } else {
                System.out.println("Password changed. Please login again.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not change password.");
        }
    }
}