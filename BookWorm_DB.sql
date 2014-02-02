DROP TABLE IF EXISTS books

CREATE TABLE books (id INT NOT NULL AUTO_INCREMENT, isbn VARCHAR(20), title VARCHAR(100), author VARCHAR(50), publicationDate VARCHAR(20), formatb VARCHAR(20), shortDescription VARCHAR(900), category VARCHAR(20),commentb VARCHAR(500), readb VARCHAR(10), PRIMARY KEY(id));

INSERT INTO books VALUES (default, '978-3-8362-1802-3', 'Java ist auch eine Insel' , 'Ullenboom, Christian', 2012, 'Gebunden', 'Java-Lehrbuch', 'Sachbuch', 'gut zu lesen', 'Ja');
INSERT INTO books VALUES (default, '978-3-8362-1507-7', 'Java 7 Mehr als eine Insel', 'Ullenboom, Christian', 2012, 'Gebunden', 'Java Lehrbuch', 'Sachbuch', 'hilfreich', 'Ja');

DROP TABLE IF EXISTS users

CREATE TABLE users (id INT NOT NULL AUTO_INCREMENT, username VARCHAR(50), userpassword VARCHAR(90), userrole VARCHAR(20); PRIMARY KEY(id));

INSERT INTO users (username, userpassword) VALUES('eva', md5('eva'), 'Administrator');  
INSERT INTO users (username, userpassword) VALUES('test', md5('test'), 'Anwender'); 
