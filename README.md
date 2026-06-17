# Trello CRUD API Testing Framework

## Overview

This project is an automated API testing framework for the Trello REST API using **Java**, **REST Assured**, **TestNG**, and **Allure Reporting**.

The framework validates the functionality of Trello resources through:

* CRUD operations (Create, Read, Update, Delete)
* Authentication validation
* Missing field validation
* Invalid parameter validation
---

## Tech Stack

| Technology    | Usage                 |
| ------------- | --------------------- |
| Java          | Programming Language  |
| REST Assured  | API Testing           |
| TestNG        | Test Framework        |
| Maven         | Dependency Management |
| Allure        | Reporting             |
| IntelliJ IDEA | IDE                   |
| Postman       | Manual API Testing    |

---

## Test Coverage

### Tier 1 - End-to-End CRUD

Tests the complete business flow:

* Create Board
* Create List
* Create Card
* Create Checklist
* Get Resources
* Update Resources
* Delete Resources

---

### Tier 2A - Authentication

Tests API authentication:

* Missing API Key
* Missing Token

Expected Result:

* HTTP 401 Unauthorized

---

### Tier 2B - Missing Fields & Invalid Parameters

Tests:

* Missing Board Name
* Missing List Name
* Missing Card Name
* Invalid Board ID
* Invalid List ID
* Invalid Card ID

---

## Repository Structure

```text
Trello-CRUD-API-Testing

src
└── test
    ├── java
    │   ├── BaseTest.java
    │   ├── AuthenticationTests.java
    │   ├── CreateGetTests.java
    │   ├── UpdateGetTests.java
    │   ├── DeleteGetTests.java
    │   ├── ParameterTests.java
    │   └── ConfigUtils.java
    │
    └── resources

allure-results

pom.xml
```

---

# Prerequisites

Install the following before running the project:

### 1. Java JDK

Recommended:

* Java 17 or later

Verify:

```bash
java -version
```

---

### 2. IntelliJ IDEA

Install IntelliJ IDEA Community or Ultimate Edition.

---

### 3. Maven

Verify:

```bash
mvn -version
```

---

### 4. Git

Verify:

```bash
git --version
```

---

### 5. Allure Commandline

Install Allure.

Verify:

```bash
allure --version
```

---

## Clone the Repository

```bash
git clone https://github.com/muhammadmedhat26/Trello-CRUD-API-Testing.git

cd Trello-CRUD-API-Testing
```

---

# Trello API Setup

Before running the tests, create your Trello API credentials.

## Step 1 - Generate API Key

Visit:

https://developer.atlassian.com/cloud/trello/guides/rest-api/api-introduction/

Generate your API Key.

---

## Step 2 - Generate Token

Generate your API Token from the Trello Developer page.

---

## Step 3 - Configure Environment Variables

Create the following environment variables:

### Windows

```cmd
setx TRELLO_API_KEY "your_api_key"

setx TRELLO_TOKEN "your_token"
```

Restart IntelliJ after setting them.

---

## Verify Environment Variables

In Command Prompt:

```cmd
echo %TRELLO_API_KEY%

echo %TRELLO_TOKEN%
```

---

# Import Project into IntelliJ

1. Open IntelliJ IDEA

2. Click:

```text
Open Project
```

3. Select:

```text
Trello-CRUD-API-Testing
```

4. Wait for Maven dependencies to download.

---

# Running Tests

## Run all tests

Using Maven:

```bash
mvn clean test
```

---

## Run specific TestNG class

Examples:

```bash
mvn -Dtest=AuthenticationTests test
```

```bash
mvn -Dtest=CreateGetTests test
```

```bash
mvn -Dtest=DeleteGetTests test
```

---

# Generate Allure Report

After test execution:

```bash
allure serve allure-results
```

Or:

```bash
allure generate allure-results --clean

allure open allure-report
```

---

# Postman Collection

This repository also includes Postman files for manual API testing.

## Import Collection

1. Open Postman

2. Click:

```text
Import
```

3. Select:

```text
Trello Collection.json
```

---

## Import Environment

1. Click:

```text
Import
```

2. Select:

```text
Trello Environment.json
```

3. Choose the imported environment from the top-right dropdown.

---

## Configure Variables


with your Trello credentials.

---

# Test Execution Report

The repository contains:

* Automated API Tests
* Test Case Excel Report
* Allure Results
* Postman Collection
* Postman Environment

---

# Author

**Muhammad Medhat**

QA Engineer | API Testing | Automation Testing

GitHub:

https://github.com/muhammadmedhat26

---
