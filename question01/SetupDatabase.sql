-- Create database
CREATE DATABASE lenden;
\c lenden;

-- Create accounts table
CREATE TABLE IF NOT EXISTS accounts (
    account_number BIGINT PRIMARY KEY,
    name VARCHAR(100),
    balance DOUBLE PRECISION
);

-- Insert sample data
INSERT INTO accounts VALUES (101, 'Alice', 5000);
INSERT INTO accounts VALUES (102, 'Bob', 3000);
INSERT INTO accounts VALUES (103, 'Charlie', 2000);
