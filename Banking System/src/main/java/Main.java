import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static BankingService bankingService = new BankingService();

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("      BANKING SYSTEM");
        System.out.println("====================================");
        
        while (true) {
            if (!authService.isLoggedIn()) {
                showMainMenu();
            } else {
                showBankingMenu();
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Register Account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Select option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                loginUser();
                break;
            case 3:
                System.out.println("Thank you for using Banking System!");
                System.exit(0);
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void showBankingMenu() {
        System.out.println("\n--- Banking Menu ---");
        System.out.println("1. Deposit Money");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Check Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Logout");
        System.out.print("Select option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                depositMoney();
                break;
            case 2:
                withdrawMoney();
                break;
            case 3:
                checkBalance();
                break;
            case 4:
                viewTransactionHistory();
                break;
            case 5:
                authService.logout();
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void registerUser() {
        System.out.println("\n--- User Registration ---");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        
        boolean success = authService.register(username, password, email, fullName);
        if (success) {
            System.out.println("Registration successful! Please login.");
        } else {
            System.out.println("Registration failed. Username/email might already exist.");
        }
    }

    private static void loginUser() {
        System.out.println("\n--- User Login ---");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        boolean success = authService.login(username, password);
        if (success) {
            System.out.println("Login successful! Welcome " + authService.getCurrentUser().getFullName());
        } else {
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private static void depositMoney() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter description (optional): ");
        String description = scanner.nextLine();
        
        boolean success = bankingService.deposit(authService.getCurrentUser().getUserId(), amount, description);
        if (success) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed!");
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Enter description (optional): ");
        String description = scanner.nextLine();
        
        boolean success = bankingService.withdraw(authService.getCurrentUser().getUserId(), amount, description);
        if (success) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Withdrawal failed! Check your balance.");
        }
    }

    private static void checkBalance() {
        double balance = bankingService.getBalance(authService.getCurrentUser().getUserId());
        System.out.printf("Your current balance: $%.2f%n", balance);
    }

    private static void viewTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        bankingService.getTransactionHistory(authService.getCurrentUser().getUserId()).forEach(System.out::println);
    }
}