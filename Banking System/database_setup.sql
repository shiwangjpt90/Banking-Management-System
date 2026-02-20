-- Create database
CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

-- Users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_balance DECIMAL(15,2) DEFAULT 0.00
);

-- Transactions table
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL, -- 'DEPOSIT', 'WITHDRAWAL'
    amount DECIMAL(15,2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert sample user (password: user123)
INSERT INTO users (username, password_hash, email, full_name, account_balance) 
VALUES ('john_doe', 'W6ph5Mm5Pz8GgiULbPgzG37mj9g=', 'john@email.com', 'John Doe', 1000.00);

-- Insert sample transaction
INSERT INTO transactions (user_id, transaction_type, amount, description) 
VALUES (1, 'DEPOSIT', 1000.00, 'Initial deposit');