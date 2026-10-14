import com.hrms.OrganizationManager;
import com.hrms.LoginManager;
import com.hrms.EmployeeManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OrganizationManager orgManager = new OrganizationManager();

        while (true) {
            System.out.println();
            System.out.println("1. Create new Organization");
            System.out.println("2. Login to Organization");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    orgManager.create(sc);
                    break;
                case "2":
                    new LoginManager().loginMenu(sc);

                    break;
                case "3":
                    System.out.println("Bye.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}