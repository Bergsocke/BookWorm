DROP TABLE IF EXISTS books

CREATE TABLE books (id INT NOT NULL AUTO_INCREMENT, isbn VARCHAR(20), title VARCHAR(100), author VARCHAR(50), publicationDate VARCHAR(20), formatb VARCHAR(20), shortDescription VARCHAR(900), category VARCHAR(20),commentb VARCHAR(500), readb VARCHAR(10), PRIMARY KEY(id));

INSERT INTO books VALUES (default, '978-3-8362-1802-3', 'Java ist auch eine Insel' , 'Ullenboom, Christian', 2012, 'Gebunden', 'Java-Lehrbuch', 'Sachbuch', 'gut zu lesen', 'ja');
