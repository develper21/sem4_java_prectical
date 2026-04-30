import java.sql.*;
import java.util.Scanner;

public class BankTransaction {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/lenden";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres21";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Account Number to debit from: ");
        long fromAccount = scanner.nextLong();
        
        System.out.print("Enter Amount to transfer: ");
        double amount = scanner.nextDouble();
        
        Connection conn = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Disable auto-commit
            conn.setAutoCommit(false);
            
            // Check balance
            String checkBalance = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement ps1 = conn.prepareStatement(checkBalance);
            ps1.setLong(1, fromAccount);
            ResultSet rs = ps1.executeQuery();
            
            if (!rs.next()) {
                System.out.println("Account not found!");
                conn.rollback();
                return;
            }
            
            double currentBalance = rs.getDouble("balance");
            
            if (currentBalance < amount) {
                conn.rollback();
                System.out.println("Transaction Failed - Insufficient Balance");
                return;
            }
            
            // Debit from source account
            String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement ps2 = conn.prepareStatement(debitQuery);
            ps2.setDouble(1, amount);
            ps2.setLong(2, fromAccount);
            ps2.executeUpdate();
            
            // Credit to account 102
            String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = 102";
            PreparedStatement ps3 = conn.prepareStatement(creditQuery);
            ps3.setDouble(1, amount);
            int rows = ps3.executeUpdate();
            
            if (rows == 0) {
                conn.rollback();
                System.out.println("Transaction Failed - Account 102 not found");
                return;
            }
            
            // Commit transaction
            conn.commit();
            System.out.println("Transaction Successful");
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
                System.out.println("Transaction Failed - " + e.getMessage());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        scanner.close();
    }
}
