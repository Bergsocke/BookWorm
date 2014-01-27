CREATE DATABASE book_database;

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, LOCK TABLES, CREATE TEMPORARY TABLES ON `book_database`.* TO 'book_user'@'localhost' IDENTIFIED BY 'book_password';

DROP TABLE IF EXISTS books

CREATE TABLE books (id INT NOT NULL AUTO_INCREMENT, isbn VARCHAR(20), title VARCHAR(100), author VARCHAR(50), publicationDate VARCHAR(20), formatb VARCHAR(20), shortDescription VARCHAR(900), commentb VARCHAR(500), readb VARCHAR(10), PRIMARY KEY(id));

INSERT INTO books VALUES (default, '978-3-8362-1802-3', 'Java ist auch eine Insel' , 'Ullenboom, Christian', 2012, 'Gebunden', 'Java-Lehrbuch', 'gut zu lesen', 'ja');
