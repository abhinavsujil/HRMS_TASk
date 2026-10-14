import com.hrms.util.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.println("Connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}