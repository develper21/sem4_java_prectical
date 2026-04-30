#!/bin/bash

cd /home/developer21/java_practical/question01

# Download PostgreSQL connector if not exists
if [ ! -f "postgresql-42.7.1.jar" ]; then
    echo "Downloading PostgreSQL JDBC Driver..."
    wget -q https://repo1.maven.org/maven2/org/postgresql/postgresql/42.7.1/postgresql-42.7.1.jar
fi

echo "Compiling..."
javac -cp ".:postgresql-42.7.1.jar" BankTransaction.java

if [ $? -eq 0 ]; then
    echo "Running Bank Transaction Program..."
    echo "================================"
    java -cp ".:postgresql-42.7.1.jar" BankTransaction
else
    echo "Compilation failed!"
fi
