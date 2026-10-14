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
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    new EmployeeManager().menu(sc, orgId);
                    break;
                case "2":
                    System.out.println("Attendance module is coming soon.");
                    break;
                case "3":
                    System.out.println("Salary module is coming soon.");
                    break;
                case "4":
                    System.out.println("Notice module is coming soon.");
                    break;
                case "5":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}