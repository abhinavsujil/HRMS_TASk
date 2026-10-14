package com.hrms;

import java.util.Scanner;

public class Dashboard {

    public void show(Scanner sc, int orgId) {
        while (true) {
            System.out.println();
            System.out.println("1. Employee");
            System.out.println("2. Attendance");
            System.out.println("3. Salary");
            System.out.println("4. Notice or Holiday");
            System.out.println("5. Logout");
            System.out.print("Choose: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Please type a number.");
                continue;
            }

            if (choice == 1) {
                new EmployeeManager().menu(sc, orgId);
            } else if (choice == 2) {
                new AttendanceManager().menu(sc, orgId);
            } else if (choice == 3) {
                new SalaryManager().menu(sc, orgId);
            } else if (choice == 4) {
                new NoticeManager().menu(sc, orgId);
            } else if (choice == 5) {
                System.out.println("Logged out.");
                break;
            } else {
                System.out.println("Wrong choice.");
            }
        }
    }
}