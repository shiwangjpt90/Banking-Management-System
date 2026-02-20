import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankingService {
    
    public boolean deposit(int userId, double amount, String description) {
        if (amount <= 0) {
            System.out.println("Amount must be positive!");
            return false;
        }
        
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Cannot connect to database!");
            return false;
        }
        
        try {
            conn.setAutoCommit(false);
            
            // Update balance
            String updateBalance = "UPDATE users SET account_balance = account_balance + ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateBalance)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
            
            // Record transaction
            String recordTransaction = "INSERT INTO transactions (user_id, transaction_type, amount, description) VALUES (?, 'DEPOSIT', ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(recordTransaction)) {
                stmt.setInt(1, userId);
                stmt.setDouble(2, amount);
                stmt.setString(3, description);
                stmt.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            System.out.println("Deposit error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
    
    public boolean withdraw(int userId, double amount, String description) {
        if (amount <= 0) {
            System.out.println("Amount must be positive!");
            return false;
        }
        
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Cannot connect to database!");
            return false;
        }
        
        try {
            conn.setAutoCommit(false);
            
            // Check sufficient balance
            String checkBalance = "SELECT account_balance FROM users WHERE user_id = ? FOR UPDATE";
            try (PreparedStatement stmt = conn.prepareStatement(checkBalance)) {
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    double currentBalance = rs.getDouble("account_balance");
                    if (currentBalance < amount) {
                        System.out.println("Insufficient balance!");
                        conn.rollback();
                        return false;
                    }
                }
            }
            
            // Update balance
            String updateBalance = "UPDATE users SET account_balance = account_balance - ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateBalance)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
            
            // Record transaction
            String recordTransaction = "INSERT INTO transactions (user_id, transaction_type, amount, description) VALUES (?, 'WITHDRAWAL', ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(recordTransaction)) {
                stmt.setInt(1, userId);
                stmt.setDouble(2, amount);
                stmt.setString(3, description);
                stmt.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            System.out.println("Withdrawal error: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
    
    public double getBalance(int userId) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) {
            System.out.println("Cannot connect to database!");
            return 0.0;
        }
        
        String query = "SELECT account_balance FROM users WHERE user_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("account_balance");
            }
        } catch (SQLException e) {
            System.out.println("Balance check error: " + e.getMessage());
        }
        return 0.0;
    }
    
    public List<String> getTransactionHistory(int userId) {
        List<String> transactions = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        
        if (conn == null) {
            System.out.println("Cannot connect to database!");
            transactions.add("Database connection failed.");
            return transactions;
        }
        
        String query = "SELECT transaction_type, amount, transaction_date, description " +
                      "FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC LIMIT 10";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String transaction = String.format("%s | Amount: $%.2f | Date: %s | Desc: %s",
                    rs.getString("transaction_type"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("transaction_date"),
                    rs.getString("description") != null ? rs.getString("description") : ""
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Transaction history error: " + e.getMessage());
        }
        
        if (transactions.isEmpty()) {
            transactions.add("No transactions found.");
        }
        
        return transactions;
    }
}