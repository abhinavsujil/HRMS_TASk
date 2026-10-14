package com.hrms;

import com.hrms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class NoticeManager {

    public void menu(Scanner sc, int orgId) {
        while (true) {
            System.out.println();
            System.out.println("1. Create notice or holiday");
            System.out.println("2. View all");
            System.out.println("3. Update");
            System.out.println("4. Delete");
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
                create(sc, orgId);
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

    public void create(Scanner sc, int orgId) {
        System.out.print("Type (notice / holiday): ");
        String type = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Message: ");
        String message = sc.nextLine();
        System.out.print("Date (yyyy-mm-dd): ");
        String date = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO notice (org_id, type, title, message, notice_date) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, orgId);
            ps.setString(2, type);
            ps.setString(3, title);
            ps.setString(4, message);
            ps.setString(5, date);
            ps.executeUpdate();
            System.out.println("Saved.");
            con.close();
        } catch (Exception e) {
            System.out.println("Could not save. Check the date format.");
        }
    }

    public void view(int orgId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM notice WHERE org_id = ?");
            ps.setInt(1, orgId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("notice_id") + " | "
                        + rs.getString("type") + " | "
                        + rs.getString("title") + " | "
                        + rs.getString("message") + " | "
                        + rs.getString("notice_date"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not show.");
        }
    }

    public void update(Scanner sc, int orgId) {
        System.out.print("Id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New title: ");
        String title = sc.nextLine();
        System.out.print("New message: ");
        String message = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE notice SET title = ?, message = ? WHERE notice_id = ? AND org_id = ?");
            ps.setString(1, title);
            ps.setString(2, message);
            ps.setInt(3, id);
            ps.setInt(4, orgId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No record with this id.");
            } else {
                System.out.println("Updated.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not update.");
        }
    }

    public void delete(Scanner sc, int orgId) {
        System.out.print("Id to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM notice WHERE notice_id = ? AND org_id = ?");
            ps.setInt(1, id);
            ps.setInt(2, orgId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No record with this id.");
            } else {
                System.out.println("Deleted.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not delete.");
        }
    }
}