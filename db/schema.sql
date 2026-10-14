#CREATE DATABASE hrms_db;
#CREATE USER 'hrms_user'@'localhost' IDENTIFIED BY 'root';
#GRANT ALL PRIVILEGES ON hrms_db.* TO 'hrms_user'@'localhost';

USE hrms_db;

CREATE TABLE organization (
    org_id          INT PRIMARY KEY AUTO_INCREMENT,
    org_name        VARCHAR(100) NOT NULL,
    address         VARCHAR(200),
    pan             VARCHAR(10) UNIQUE,
    gst             VARCHAR(15) UNIQUE,
    department      VARCHAR(100),
    email           VARCHAR(100) NOT NULL UNIQUE,
    password        VARCHAR(100) NOT NULL,
    employee_count  INT
);

CREATE TABLE employee (
    emp_id    INT PRIMARY KEY AUTO_INCREMENT,
    org_id    INT NOT NULL,
    name      VARCHAR(100) NOT NULL,
    email     VARCHAR(100),
    phone     VARCHAR(15),
    joined_on DATE,
    FOREIGN KEY (org_id) REFERENCES organization(org_id) ON DELETE CASCADE
);

CREATE TABLE attendance (
    att_id    INT PRIMARY KEY AUTO_INCREMENT,
    emp_id    INT NOT NULL,
    att_date  DATE NOT NULL,
    status    VARCHAR(20) NOT NULL,
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE salary (
    salary_id  INT PRIMARY KEY AUTO_INCREMENT,
    emp_id     INT NOT NULL,
    basic      INT NOT NULL,
    bonus      INT DEFAULT 0,
    deduction  INT DEFAULT 0,
    month_name VARCHAR(20),
    FOREIGN KEY (emp_id) REFERENCES employee(emp_id) ON DELETE CASCADE
);

CREATE TABLE notice (
    notice_id  INT PRIMARY KEY AUTO_INCREMENT,
    org_id     INT NOT NULL,
    type       VARCHAR(10) NOT NULL,
    title      VARCHAR(100) NOT NULL,
    message    VARCHAR(500),
    notice_date DATE,
    FOREIGN KEY (org_id) REFERENCES organization(org_id) ON DELETE CASCADE
);

DESC employee;
SHOW tables;