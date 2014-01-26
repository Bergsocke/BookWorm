package bookDatabasePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/**
 * Die Klasse "BookDB" stellte eine Verbindung zur MySQL-Datenbank
 * "book_database" her und bietet die Methoden zum Anzeigen, Suchen, Speichern
 * und L�schen von Datens�tzen aus der Tabelle "books"
 * 
 * @author Bergsocke
 * 
 */
public class BookDB {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;
	// Variable, die anzeigen soll, ob das Speichern, Updaten oder L�schen eines
	// Datensatzes erfolgreich war
	public static int successful = 0;

	/**
	 * Diese Methode baut die Datenbankverbindung zur Datenbank "book_database"
	 * auf
	 * 
	 * @return connect
	 */
	public static Connection connectDB() {

		try {
			// Treiber wird geladen und die Regestrierung beim DriverManager
			// erfolgt
			Class.forName("com.mysql.jdbc.Driver");

			// DriverManager wird verwenden und die Verbindung zur DB wird
			// aufgebaut
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/book_database", "book_user",
					"book_password");

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankverbindung konnte nicht hergestellt werden. "
							+ "Bitte pr�fen Sie, ob der MySQL-Server l�uft.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}

		return connect;
	}

	/**
	 * Es werden alle Datens�tze, die in der Tabelle "books" vorhanden sind,
	 * angezeigt
	 * 
	 * @return bookList
	 */
	public static List<Book> displayAll() {

		List<Book> bookList = new ArrayList<Book>();

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books;");

			// SQL-Befehl wird ausgef�hrt
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				bookList.add(new Book(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4), myResultSet.getString(5), myResultSet
						.getString(6), myResultSet.getString(7), myResultSet
						.getString(8), myResultSet.getString(9)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankabfrage konnte nicht durchgef�hrt werden.",
					"Fehler", JOptionPane.ERROR_MESSAGE);

		} finally {
			// offene Verbindungen werden geschlossen
			closeConnections();
		}

		return bookList;
	}

	/**
	 * Methode zum Suchen nach dem Buchtitel in der Tabelle "books"
	 * 
	 * @param bookTitle
	 * @return bookList
	 */
	public static List<Book> findByTitle(String bookTitle) {

		List<Book> bookList = new ArrayList<Book>();

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books WHERE title LIKE '%"
							+ bookTitle + "%';");

			// SQL-Befehl wird ausgef�hrt
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				bookList.add(new Book(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4), myResultSet.getString(5), myResultSet
						.getString(6), myResultSet.getString(7), myResultSet
						.getString(8), myResultSet.getString(9)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankabfrage konnte nicht durchgef�hrt werden.",
					"Fehler", JOptionPane.ERROR_MESSAGE);

		} finally {
			// offene Verbindungen werden geschlossen
			closeConnections();
		}

		return bookList;
	}

	/**
	 * Methode zum Suchen nach der Buch-ID in der Tabelle "books"
	 * 
	 * @param bookID
	 * @return selectedBook
	 */
	public static Book findByID(String bookID) {

		Book foundBook = null;

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books WHERE id LIKE "
							+ bookID + ";");

			// SQL-Befehl wird ausgef�hrt
			myResultSet = myPreparedStatement.executeQuery();

			// da das Select-Statement immer nur genau einen oder keinen
			// Datensatz liefern kann, gen�gt hier diese Abfrage
			if (myResultSet.next()) {
				foundBook = new Book(myResultSet.getString(1),
						myResultSet.getString(2), myResultSet.getString(3),
						myResultSet.getString(4), myResultSet.getString(5),
						myResultSet.getString(6), myResultSet.getString(7),
						myResultSet.getString(8), myResultSet.getString(9));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankabfrage konnte nicht durchgef�hrt werden.",
					"Fehler", JOptionPane.ERROR_MESSAGE);

		} finally {
			// offene Verbindungen werden geschlossen
			closeConnections();
		}

		return foundBook;
	}

	/**
	 * Methode zum Speichern eines neuen Datensatzes in die Tabelle "books"
	 * 
	 * @param bookToSave
	 * @return successful
	 */
	public static int saveBook(Book bookToSave) {

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("INSERT INTO book_database.books VALUES(default,?, ?, ?, ?, ?, ?, ?, ?)");

			myPreparedStatement.setString(1, bookToSave.getIsbn());
			myPreparedStatement.setString(2, bookToSave.getTitle());
			myPreparedStatement.setString(3, bookToSave.getAuthor());
			myPreparedStatement.setString(4, bookToSave.getPublicationDate());
			myPreparedStatement.setString(5, bookToSave.getFormat());
			myPreparedStatement.setString(6, bookToSave.getShortDescription());
			myPreparedStatement.setString(7, bookToSave.getComment());
			myPreparedStatement.setString(8, bookToSave.getRead());

			// SQL-Befehl wird ausgef�hrt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbank-Fehler beim Abspeichern eines Datensatzes",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum �ndern eines bereits vorhandenen Datensatzes in der Tabelle
	 * "books"
	 * 
	 * @param bookToUpdate
	 * @return successful
	 */
	public static int updateBook(Book bookToUpdate) {

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("UPDATE book_database.books SET isbn = '"
							+ bookToUpdate.getIsbn() + "', title = '"
							+ bookToUpdate.getTitle() + "', author = '"
							+ bookToUpdate.getAuthor()
							+ "', publicationDate = '"
							+ bookToUpdate.getPublicationDate()
							+ "', formatb = '" + bookToUpdate.getFormat()
							+ "', shortDescription = '"
							+ bookToUpdate.getShortDescription()
							+ "', commentb = '" + bookToUpdate.getComment()
							+ "', readb = '" + bookToUpdate.getRead()
							+ "' WHERE id = " + bookToUpdate.getId() + ";");

			// SQL-Befehl wird ausgef�hrt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbank-Fehler beim �ndern eines Datensatzes", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum L�schen eines Datensatzes aus der Tabelle "books"
	 * 
	 * @param bookID
	 * @return successful
	 */
	public static int deleteBook(String bookID) {

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("DELETE FROM book_database.books WHERE id = "
							+ bookID + ";");

			// SQL-Befehl wird ausgef�hrt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbank-Fehler beim L�schen eines Datensatzes",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum Schlie�en aller offenen Verbindungen
	 */
	public static void closeConnections() {
		try {

			if (myResultSet != null) {
				myResultSet.close();
			}

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			if (connect != null) {
				connect.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Verbindungen konnten nicht geschlossen werden.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}