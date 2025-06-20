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
    description VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    complain_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'pending',
    resolved_date DATE NULL,
    resolved_time TIME NULL,
    FOREIGN KEY (user_id) REFERENCES user(UId)
);
