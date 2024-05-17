import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String jdbcURL = "jdbc:mysql://localhost:3306/customer_data";
        String dbUser = "Kavi";
        String dbPassword = "Qwert@123";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            System.out.println("Connected to the database.");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Subscribed (true/false): ");
            boolean subscribed = scanner.nextBoolean();

            System.out.print("Enter favorite number: ");
            int favoriteNumber = scanner.nextInt();

            // Check if email already exists
            String checkQuery = "SELECT COUNT(*) FROM users WHERE email = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("This email is already registered.");
                } else {
                    // Insert new user
                    String insertQuery = "INSERT INTO users (email, subscribed, favorite_number) VALUES (?, ?, ?)";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, email);
                        insertStmt.setBoolean(2, subscribed);
                        insertStmt.setInt(3, favoriteNumber);
                        insertStmt.executeUpdate();
                        System.out.println("User added successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

