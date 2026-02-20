import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Skt@9041644226";
    
    private static Connection connection = null;
    
    static {
        // Load driver when class is first used
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: MySQL Driver not found!");
            System.out.println("Make sure 'mysql-connector-java-8.0.33.jar' is in the same folder");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (SQLException e) {
                System.out.println("Database connection failed!");
                System.out.println("Error: " + e.getMessage());
                System.out.println("Check: ");
                System.out.println("1. Is MySQL running? (Type: net start MySQL80)");
                System.out.println("2. Is password correct? Your password: " + PASSWORD);
                System.out.println("3. Is database 'banking_system' created?");
            }
        }
        return connection;
    }
}