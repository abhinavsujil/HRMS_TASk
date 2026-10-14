package com.hrms;

import com.hrms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AttendanceManager {

    public void menu(Scanner sc, int orgId) {
        while (true) {
            System.out.println();
            System.out.println("1. Mark attendance");
            System.out.println("2. View attendance of an employee");
            System.out.println("3. Update attendance");
            System.out.println("4. Delete attendance");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                mark(sc, orgId);
            } else if (choice == 2) {
                view(sc, orgId);
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

    // returns true only if this employee belongs to the logged in organization
    public boolean checkEmployee(int empId, int orgId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM employee WHERE emp_id = ? AND org_id = ?");
            ps.setInt(1, empId);
            ps.setInt(2, orgId);
            ResultSet rs = ps.executeQuery();
            boolean found = rs.next();
            con.close();
            return found;
        } catch (Exception e) {
            return false;
        }
    }

    public void mark(Scanner sc, int orgId) {
        System.out.print("Employee id: ");
        int empId = Integer.parseInt(sc.nextLine());
        if (!checkEmployee(empId, orgId)) {
            System.out.println("No such employee.");
            return;
        }
        System.out.print("Date (yyyy-mm-dd): ");
        String date = sc.nextLine();
        System.out.print("Status (Present / Absent / Leave): ");
        String status = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO attendance (emp_id, att_date, status) VALUES (?, ?, ?)");
            ps.setInt(1, empId);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();
            System.out.println("Attendance saved.");
            con.close();
        } catch (Exception e) {
            System.out.println("Something went wrong. Check the date format.");
        }
    }

    public void view(Scanner sc, int orgId) {
        System.out.print("Employee id: ");
        int empId = Integer.parseInt(sc.nextLine());
        if (!checkEmployee(empId, orgId)) {
            System.out.println("No such employee.");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM attendance WHERE emp_id = ?");
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("att_id") + " | "
                        + rs.getString("att_date") + " | "
                        + rs.getString("status"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not show attendance.");
        }
    }

    public void update(Scanner sc, int orgId) {
        System.out.print("Employee id: ");
        int empId = Integer.parseInt(sc.nextLine());
        if (!checkEmployee(empId, orgId)) {
            System.out.println("No such employee.");
            return;
        }
        System.out.print("Date to change (yyyy-mm-dd): ");
        String date = sc.nextLine();
        System.out.print("New status: ");
        String status = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE attendance SET status = ? WHERE emp_id = ? AND att_date = ?");
            ps.setString(1, status);
            ps.setInt(2, empId);
            ps.setString(3, date);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No record for this date.");
            } else {
                System.out.println("Updated.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not update.");
        }
    }

    public void delete(Scanner sc, int orgId) {
        System.out.print("Employee id: ");
        int empId = Integer.parseInt(sc.nextLine());
        if (!checkEmployee(empId, orgId)) {
            System.out.println("No such employee.");
            return;
        }
        System.out.print("Date to delete (yyyy-mm-dd): ");
        String date = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM attendance WHERE emp_id = ? AND att_date = ?");
            ps.setInt(1, empId);
            ps.setString(2, date);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No record for this date.");
            } else {
                System.out.println("Deleted.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not delete.");
        }
    }
}