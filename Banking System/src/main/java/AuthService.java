import java.sql.*;

public class AuthService {
    private User currentUser = null;
    
    public boolean register(String username, String password, String email, String fullName) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Cannot connect to database!");
            return false;
        }
        
        String hashedPassword = SecurityUtils.hashPassword(password);
        
        String query = "INSERT INTO users (username, password_hash, email, full_name) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, email);
            stmt.setString(4, fullName);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean login(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Cannot connect to database!");
            return false;
        }
        
        String query = "SELECT * FROM users WHERE username = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (SecurityUtils.verifyPassword(password, storedHash)) {
                    currentUser = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("full_name"),
                        rs.getDouble("account_balance")
                    );
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return false;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
}