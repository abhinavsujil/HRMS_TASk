package com.hrms;

import com.hrms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class SalaryManager {

    public void menu(Scanner sc, int orgId) {
        while (true) {
            System.out.println();
            System.out.println("1. Create salary");
            System.out.println("2. View salary of an employee");
            System.out.println("3. Update salary");
            System.out.println("4. Delete salary");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            if (choice == 1) {
                create(sc, orgId);
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

    // true only if this employee belongs to the logged in organization
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

    public void create(Scanner sc, int orgId) {
        System.out.print("Employee id: ");
        int empId = Integer.parseInt(sc.nextLine());
        if (!checkEmployee(empId, orgId)) {
            System.out.println("No such employee.");
            return;
        }
        System.out.print("Month (example: October 2026): ");
        String month = sc.nextLine();
        System.out.print("Basic salary: ");
        int basic = Integer.parseInt(sc.nextLine());
        System.out.print("Bonus: ");
        int bonus = Integer.parseInt(sc.nextLine());
        System.out.print("Deduction: ");
        int deduction = Integer.parseInt(sc.nextLine());

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO salary (emp_id, basic, bonus, deduction, month_name) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, empId);
            ps.setInt(2, basic);
            ps.setInt(3, bonus);
            ps.setInt(4, deduction);
            ps.setString(5, month);
            ps.executeUpdate();
            System.out.println("Salary saved.");
            con.close();
        } catch (Exception e) {
            System.out.println("Could not save salary.");
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
                    "SELECT * FROM salary WHERE emp_id = ?");
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int basic = rs.getInt("basic");
                int bonus = rs.getInt("bonus");
                int deduction = rs.getInt("deduction");
                int net = basic + bonus - deduction;
                System.out.println(rs.getString("month_name") + " | basic " + basic
                        + " | bonus " + bonus + " | deduction " + deduction
                        + " | net " + net);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not show salary.");
        }
    }

    public void update(Scanner sc, int orgId) {
        System.out.print("Employee id: ");
        int empId = Integer.parseInt(sc.nextLine());
        if (!checkEmployee(empId, orgId)) {
            System.out.println("No such employee.");
            return;
        }
        System.out.print("Month to change (example: October 2026): ");
        String month = sc.nextLine();
        System.out.print("New basic salary: ");
        int basic = Integer.parseInt(sc.nextLine());
        System.out.print("New bonus: ");
        int bonus = Integer.parseInt(sc.nextLine());
        System.out.print("New deduction: ");
        int deduction = Integer.parseInt(sc.nextLine());

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE salary SET basic = ?, bonus = ?, deduction = ? WHERE emp_id = ? AND month_name = ?");
            ps.setInt(1, basic);
            ps.setInt(2, bonus);
            ps.setInt(3, deduction);
            ps.setInt(4, empId);
            ps.setString(5, month);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No salary found for this month.");
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
        System.out.print("Month to delete (example: October 2026): ");
        String month = sc.nextLine();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM salary WHERE emp_id = ? AND month_name = ?");
            ps.setInt(1, empId);
            ps.setString(2, month);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No salary found for this month.");
            } else {
                System.out.println("Deleted.");
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Could not delete.");
        }
    }
}