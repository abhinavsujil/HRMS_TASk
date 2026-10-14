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
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    int orgId = login(sc);
                    if (orgId != -1) {
                        new Dashboard().show(sc, orgId);
                        return;
                    }
                    break;
                case "2":
                    forgotPassword(sc);
                    break;
                case "3":
                    return;
                case "4":
                    System.out.println("Bye.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private int login(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        String sql = "SELECT org_id, org_name FROM organization WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Welcome to " + rs.getString("org_name"));
                    return rs.getInt("org_id");
                }
            }
            System.out.println("Wrong email or password.");

        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return -1;
    }

    private void forgotPassword(Scanner sc) {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = sc.nextLine();

        String sql = "UPDATE organization SET password = ? WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No organization found with this email.");
            } else {
                System.out.println("Password changed. Please login again.");
            }

        } catch (Exception e) {
            System.out.println("Could not change password: " + e.getMessage());
        }
    }
}