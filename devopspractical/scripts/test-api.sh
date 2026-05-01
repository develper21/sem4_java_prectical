#!/bin/bash

# API Testing Script
# ==================

BASE_URL="http://localhost:8080/api/products"

echo "=========================================="
echo "Testing Product API Endpoints"
echo "=========================================="

# Test Health Check
echo ""
echo "1. Testing Health Endpoint..."
curl -s ${BASE_URL}/health | jq . 2>/dev/null || curl -s ${BASE_URL}/health

# Create Product
echo ""
echo ""
echo "2. Creating Product..."
CREATE_RESPONSE=$(curl -s -X POST ${BASE_URL} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Laptop",
    "description": "High-performance test laptop",
    "price": 999.99,
    "quantity": 10
  }')
echo ${CREATE_RESPONSE} | jq . 2>/dev/null || echo ${CREATE_RESPONSE}

# Get All Products
echo ""
echo ""
echo "3. Getting All Products..."
curl -s ${BASE_URL} | jq . 2>/dev/null || curl -s ${BASE_URL}

# Get Product Count
echo ""
echo ""
echo "4. Getting Product Count..."
curl -s ${BASE_URL}/count | jq . 2>/dev/null || curl -s ${BASE_URL}/count

# Search Products
echo ""
echo ""
echo "5. Searching Products..."
curl -s "${BASE_URL}/search?name=Laptop" | jq . 2>/dev/null || curl -s "${BASE_URL}/search?name=Laptop"

echo ""
echo ""
echo "=========================================="
echo "API Tests Completed!"
echo "=========================================="
