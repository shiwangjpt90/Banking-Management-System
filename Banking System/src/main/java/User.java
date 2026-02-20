public class User {
    private int userId;
    private String username;
    private String email;
    private String fullName;
    private double accountBalance;
    
    public User(int userId, String username, String email, String fullName, double accountBalance) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.accountBalance = accountBalance;
    }
    
    // Getters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public double getAccountBalance() { return accountBalance; }
    
    @Override
    public String toString() {
        return String.format("User: %s (Balance: $%.2f)", fullName, accountBalance);
    }
}