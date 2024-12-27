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
                      priority ENUM('low', 'medium', 'high') NOT NULL
);

CREATE TABLE task_user (
                           task_id INT NOT NULL,
                           user_id INT NOT NULL,
                           PRIMARY KEY (task_id, user_id),
                           FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE ON UPDATE CASCADE,
                           FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE task_category (
                               task_id INT NOT NULL,
                               category_id INT NOT NULL,
                               PRIMARY KEY (task_id, category_id),
                               FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE ON UPDATE CASCADE,
                               FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);
