CREATE DATABASE pahanaedu;
USE pahanaedu;

CREATE TABLE users (
                       username VARCHAR(50) PRIMARY KEY,
                       password VARCHAR(50) NOT NULL
);

CREATE TABLE customers (
                           account_number VARCHAR(50) PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           address VARCHAR(200) NOT NULL,
                           telephone VARCHAR(20) NOT NULL,
                           units_consumed INT NOT NULL
);

CREATE TABLE items (
                       item_id VARCHAR(50) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       price DOUBLE NOT NULL,
                       stock INT NOT NULL
);

CREATE TABLE bills (
                       bill_id INT AUTO_INCREMENT PRIMARY KEY,
                       account_number VARCHAR(50),
                       items VARCHAR(1000) NOT NULL,
                       total DOUBLE NOT NULL,
                       date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (account_number) REFERENCES customers(account_number)
);

INSERT INTO users (username, password) VALUES ('admin', 'admin123');