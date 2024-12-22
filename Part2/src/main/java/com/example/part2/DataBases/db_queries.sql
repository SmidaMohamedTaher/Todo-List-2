DROP DATABASE IF EXISTS Dolist;
CREATE DATABASE Dolist;
USE Dolist;

CREATE TABLE user (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(30) NOT NULL,
                      password VARCHAR(255) NOT NULL
);

CREATE TABLE category (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          category VARCHAR(30) NOT NULL
);

CREATE TABLE task (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(30) NOT NULL,
                      description VARCHAR(1000),
                      dueDate DATE,
                      status ENUM('completed', 'not_completed', 'abandoned') NOT NULL,
                      priority ENUM('low', 'medium', 'high') NOT NULL,
                      user_id INT,
                      category_id INT,
                      FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
                      FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL ON UPDATE CASCADE
);