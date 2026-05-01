#!/bin/bash

# Run Local Script for Spring Boot CRUD Application
# =================================================

set -e

echo "=========================================="
echo "Spring Boot CRUD - Local Runner"
echo "=========================================="

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Error: Maven is not installed${NC}"
    exit 1
fi

# Check Java version
echo -e "${BLUE}Checking Java version...${NC}"
java -version

echo ""
echo -e "${BLUE}Building application...${NC}"
mvn clean package -DskipTests

echo ""
echo -e "${GREEN}Build successful!${NC}"
echo ""
echo -e "${BLUE}Starting application...${NC}"
echo -e "${GREEN}Application will be available at: http://localhost:8080${NC}"
echo -e "${GREEN}H2 Console: http://localhost:8080/h2-console${NC}"
echo ""

# Run the application
java -jar target/spring-boot-crud-*.jar
