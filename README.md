# Complaint Management System

A web-based Complaint Management System built using Java Servlets, JSP, and MySQL. The system allows users to register and submit complaints, and administrators to manage and resolve them efficiently.

---

## 🧾 Project Overview

**Technologies Used:**
- Java (Servlets + JSP)
- HTML/CSS/JavaScript
- jQuery & Bootstrap 5
- MySQL (Database)
- Apache Tomcat (Servlet Container)

**Main Features:**
- User login and role-based access
- Complaint submission with status tracking
- Admin dashboard to view, update, and delete complaints
- Modal-based form handling
- Data persistence using JDBC

---

## ⚙️ Setup and Configuration Guide

### 1. 📦 Requirements
- JDK 11 or later
- Apache Tomcat 9+
- MySQL Server
- Maven or IntelliJ IDEA (or any IDE)
- BasicDataSource / connection pool library (e.g., Apache DBCP)

---

### 2. 🔧 Database Setup

```sql
create database cms;
use cms;
CREATE TABLE user (
    UId VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    nic VARCHAR(12) UNIQUE NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    active_status BOOLEAN,
    role ENUM('Employee', 'Admin') NOT NULL
);
CREATE TABLE Complaint (
    CID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    complain_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'pending',
    resolved_date DATE NULL,
    resolved_time TIME NULL,
    FOREIGN KEY (user_id) REFERENCES user(UId)
);