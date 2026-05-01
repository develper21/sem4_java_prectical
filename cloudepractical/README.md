# Spring Boot CRUD Application with Jenkins CI Pipeline

## 📋 Overview

This is a **complete solution for Question 3: Jenkins CI Pipeline Setup** exam requirements.

**Features Implemented:**
- ✅ Minimal CRUD operations for Product entity
- ✅ RESTful API endpoints
- ✅ Spring Boot + Maven build
- ✅ Unit & Integration tests
- ✅ Jenkins Pipeline with all required stages

---

## 🏗️ Project Structure

```
cloudepractical/
├── pom.xml                          # Maven configuration
├── Jenkinsfile                      # CI/CD Pipeline definition
├── README.md                        # This guide
├── src/
│   ├── main/
│   │   ├── java/com/example/crud/
│   │   │   ├── CrudApplication.java              # Main entry point
│   │   │   ├── entity/Product.java               # JPA Entity
│   │   │   ├── repository/ProductRepository.java # Data access
│   │   │   ├── service/ProductService.java       # Business logic
│   │   │   └── controller/ProductController.java # REST API
│   │   └── resources/
│   │       └── application.properties            # App configuration
│   └── test/
│       └── java/com/example/crud/
│           ├── CrudApplicationTests.java           # Context test
│           ├── ProductServiceTest.java            # Service unit tests
│           └── ProductControllerIntegrationTest.java # API tests
└── target/                          # Build output (generated)
```

---

## 🚀 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products/health` | Health check |
| POST | `/api/products` | Create product |
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET | `/api/products/search?name={name}` | Search products |
| GET | `/api/products/count` | Get product count |
| GET | `/api/products/in-stock` | Get products in stock |

---

## 📊 Jenkins Pipeline Stages

```
┌─────────────────────────────────────────────────────────────┐
│                     JENKINS PIPELINE                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1️⃣ CHECKOUT                                               │
│     ↳ Pull code from GitHub repository                     │
│                                                             │
│  2️⃣ BUILD                                                  │
│     ↳ mvn clean compile                                    │
│                                                             │
│  3️⃣ TEST                                                   │
│     ↳ mvn test                                             │
│     ↳ Publish JUnit reports                                │
│                                                             │
│  4️⃣ CODE QUALITY (Optional)                                │
│     ↳ JaCoCo code coverage                                 │
│                                                             │
│  5️⃣ PACKAGE                                                │
│     ↳ mvn package                                          │
│     ↳ Generate JAR artifact                                │
│                                                             │
│  6️⃣ ARCHIVE                                                │
│     ↳ Store build artifacts                                │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Step-by-Step Setup Guide

### **Part 1: Local Development Setup**

#### Step 1: Install Prerequisites

```bash
# Check Java version (need JDK 17)
java -version

# Check Maven
mvn -version

# If not installed, install them:
# Ubuntu/Debian:
sudo apt update
sudo apt install openjdk-17-jdk maven -y

# macOS:
brew install openjdk@17 maven
```

#### Step 2: Run the Application Locally

```bash
# Navigate to project
cd /home/developer21/Documents/Portfolios/Portfolio/cloudepractical

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Application starts on http://localhost:8080
```

#### Step 3: Test the API

```bash
# Test health endpoint
curl http://localhost:8080/api/products/health

# Create a product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "Gaming laptop",
    "price": 999.99,
    "quantity": 10
  }'

# Get all products
curl http://localhost:8080/api/products

# Access H2 Console (for database view)
# Open: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:cruddb
# Username: sa
```

---

### **Part 2: Jenkins Setup**

#### Step 1: Install Jenkins

```bash
# Ubuntu/Debian installation:

# Add Jenkins repository key
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null

# Add Jenkins repository
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

# Update and install
sudo apt update
sudo apt install jenkins -y

# Start Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins

# Get initial admin password
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

#### Step 2: Configure Jenkins

1. **Access Jenkins:**
   - Open: `http://localhost:8080`
   - Enter the initial admin password
   - Install suggested plugins
   - Create admin user

2. **Install Required Plugins:**
   - Go to: `Manage Jenkins > Manage Plugins > Available`
   - Install these plugins:
     - **Maven Integration**
     - **Git Plugin** (usually pre-installed)
     - **Pipeline**
     - **JUnit Plugin**
     - **JaCoCo Plugin**
   - Restart Jenkins after installation

3. **Configure Tools:**
   - Go to: `Manage Jenkins > Global Tool Configuration`
   
   **Configure JDK:**
   - Click "Add JDK"
   - Name: `JDK17`
   - JAVA_HOME: `/usr/lib/jvm/java-17-openjdk-amd64`
   
   **Configure Maven:**
   - Click "Add Maven"
   - Name: `Maven3`
   - Version: `3.9.x` (latest)
   - Or use: `Install automatically`

#### Step 3: Create Jenkins Pipeline Job

1. **Create New Job:**
   - Click `New Item`
   - Enter name: `spring-boot-crud-pipeline`
   - Select `Pipeline`
   - Click `OK`

2. **Configure Pipeline:**
   
   In the job configuration:
   
   **General Section:**
   - Check `Discard old builds`
   - Max # of builds to keep: `5`
   
   **Pipeline Section:**
   - Definition: `Pipeline script from SCM`
   - SCM: `Git`
   - Repository URL: `https://github.com/yourusername/spring-boot-crud.git`
   - Branch specifier: `*/main`
   - Script Path: `Jenkinsfile`
   
   **OR use inline Pipeline script:**
   - Definition: `Pipeline script`
   - Copy contents from the `Jenkinsfile` in this project

3. **Add GitHub Credentials (if private repo):**
   - Go to: `Manage Jenkins > Manage Credentials`
   - Click `(global)` domain
   - Add Credentials:
     - Kind: `Username with password` OR `Secret text` (for PAT)
     - ID: `github-credentials`
     - Enter username and password/token

#### Step 4: Configure GitHub Webhook (Optional - for automatic triggers)

1. **In GitHub Repository:**
   - Go to: `Settings > Webhooks > Add webhook`
   - Payload URL: `http://your-jenkins-url:8080/github-webhook/`
   - Content type: `application/json`
   - Events: `Just the push event`
   - Click `Add webhook`

2. **In Jenkins Job:**
   - Check `GitHub hook trigger for GITScm polling`

---

### **Part 3: Running the Pipeline**

#### Step 1: Manual Build

1. Go to your pipeline job: `spring-boot-crud-pipeline`
2. Click `Build Now`
3. Watch the stages execute in the Stage View

#### Step 2: View Results

- **Console Output:** Click on the build number > `Console Output`
- **Test Results:** Build > `Test Results`
- **Code Coverage:** Build > `Coverage Report`
- **Artifacts:** Build > `Artifacts` (download the JAR file)

---

## 📦 Generated Artifacts

After successful pipeline execution, you'll find:

```
target/
├── spring-boot-crud-1.0.0.jar          # Main application JAR
├── surefire-reports/                    # Test reports
│   ├── com.example.crud.CrudApplicationTests.txt
│   └── TEST-*.xml
├── site/jacoco/                         # Code coverage reports
│   └── index.html
└── classes/                             # Compiled classes
```

---

## 🔧 Configuration Reference

### Jenkinsfile Variables to Update

```groovy
// Line 47-49: Update with your repository
git(
    url: 'https://github.com/YOUR_USERNAME/YOUR_REPO.git',  // ← CHANGE THIS
    branch: 'main',
    credentialsId: 'github-credentials'                        // ← CHANGE THIS
)
```

### Application Ports

| Service | Port | URL |
|---------|------|-----|
| Spring Boot App | 8080 | http://localhost:8080 |
| H2 Console | 8080 | http://localhost:8080/h2-console |
| Jenkins | 8080 | http://localhost:8080 (if separate) |

---

## 🧪 Testing Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ProductServiceTest

# Run with code coverage
mvn clean test jacoco:report

# Package without tests
mvn clean package -DskipTests
```

---

## 🐛 Troubleshooting

### Common Issues:

**1. Port 8080 already in use**
```bash
# Find process using port 8080
sudo lsof -i :8080

# Kill the process
sudo kill -9 <PID>
```

**2. Maven not found in Jenkins**
- Go to: `Manage Jenkins > Global Tool Configuration`
- Add Maven installation with name `Maven3`
- Update `Jenkinsfile` to use `tool name: 'Maven3', type: 'maven'`

**3. GitHub authentication failed**
- Verify credentials in `Manage Jenkins > Credentials`
- Ensure PAT (Personal Access Token) has `repo` scope
- Check repository URL format

**4. Tests failing in Jenkins but passing locally**
- Check if `application-test.properties` exists
- Verify H2 database configuration
- Check if tests are environment-dependent

---

## 📚 Summary: What You Need to Set Up

### ✅ After This Practical, You Need:

| # | Setup Item | Purpose | Priority |
|---|------------|---------|----------|
| 1 | **Java JDK 17** | Run Spring Boot | Required |
| 2 | **Maven 3.9+** | Build tool | Required |
| 3 | **Jenkins Server** | CI/CD automation | Required |
| 4 | **GitHub Repository** | Host code | Required |
| 5 | **Jenkins Plugins** | Maven, Pipeline, JUnit | Required |
| 6 | **Credentials in Jenkins** | GitHub access | Required |
| 7 | **JDK & Maven in Jenkins** | Build tools config | Required |
| 8 | **GitHub Webhook** | Auto-trigger builds | Optional |
| 9 | **Email Notifications** | Build alerts | Optional |
| 10 | **SonarQube** | Code quality gates | Optional |

---

## 📝 Exam Submission Checklist

- [ ] Project builds with `mvn clean install`
- [ ] All tests pass (`mvn test`)
- [ ] Jenkinsfile has 6 stages (Checkout, Build, Test, Code Quality, Package, Archive)
- [ ] JAR artifact is generated
- [ ] API endpoints work correctly
- [ ] Code has proper documentation/comments

---

## 🎯 Quick Commands Reference

```bash
# FULL WORKFLOW:

# 1. Build
mvn clean compile

# 2. Test
mvn test

# 3. Package
mvn package

# 4. Run
java -jar target/spring-boot-crud-1.0.0.jar
```

---

**Created for Cloud/DevOps Practical Examination**
**Question 3: Jenkins CI Pipeline Setup** ✓
