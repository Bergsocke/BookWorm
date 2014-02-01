package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import view.InfoError;

/**
 * Die Klasse "BookDB" stellte eine Verbindung zur MySQL-Datenbank
 * "book_database" her und bietet die Methoden zum Anzeigen, Suchen, Speichern
 * und Löschen von Datensätzen der Tabelle "books"
 * 
 * @author Bergsocke
 * 
 */
public class BookDB {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;
	// Variable, die anzeigen soll, ob das Speichern, Updaten oder Löschen eines
	// Datensatzes erfolgreich war
	public static int successful = 0;

	/**
	 * Es werden alle Datensätze, die in der Tabelle "books" vorhanden sind,
	 * angezeigt
	 * 
	 * @return bookList
	 */
	public static List<Book> displayAll() {

		List<Book> bookList = new ArrayList<Book>();

		try {
			// Datenbankverbindung herstellen
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books;");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				bookList.add(new Book(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4), myResultSet.getString(5), myResultSet
						.getString(6), myResultSet.getString(7), myResultSet
						.getString(8), myResultSet.getString(9), myResultSet
						.getString(10)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			InfoError.showMessage(errorText);

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
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books WHERE title LIKE '%"
							+ bookTitle + "%';");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				bookList.add(new Book(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4), myResultSet.getString(5), myResultSet
						.getString(6), myResultSet.getString(7), myResultSet
						.getString(8), myResultSet.getString(9), myResultSet
						.getString(10)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			InfoError.showMessage(errorText);

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
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books WHERE id LIKE "
							+ bookID + ";");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

			// da das Select-Statement immer nur genau einen oder keinen
			// Datensatz liefern kann, genügt hier diese Abfrage
			if (myResultSet.next()) {
				foundBook = new Book(myResultSet.getString(1),
						myResultSet.getString(2), myResultSet.getString(3),
						myResultSet.getString(4), myResultSet.getString(5),
						myResultSet.getString(6), myResultSet.getString(7),
						myResultSet.getString(8), myResultSet.getString(9),
						myResultSet.getString(10));
			}

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			InfoError.showMessage(errorText);

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
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("INSERT INTO book_database.books VALUES(default,?, ?, ?, ?, ?, ?, ?, ?, ?)");

			myPreparedStatement.setString(1, bookToSave.getIsbn());
			myPreparedStatement.setString(2, bookToSave.getTitle());
			myPreparedStatement.setString(3, bookToSave.getAuthor());
			myPreparedStatement.setString(4, bookToSave.getPublicationDate());
			myPreparedStatement.setString(5, bookToSave.getFormat());
			myPreparedStatement.setString(6, bookToSave.getShortDescription());
			myPreparedStatement.setString(7, bookToSave.getCategory());
			myPreparedStatement.setString(8, bookToSave.getComment());
			myPreparedStatement.setString(9, bookToSave.getRead());

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Abspeichern eines Datensatzes.";
			InfoError.showMessage(errorText);

			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum Ändern eines bereits vorhandenen Datensatzes in der Tabelle
	 * "books"
	 * 
	 * @param bookToUpdate
	 * @return successful
	 */
	public static int updateBook(Book bookToUpdate) {

		try {
			// Datenbankverbindung herstellen
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
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
							+ "', category = '" + bookToUpdate.getCategory()
							+ "', commentb = '" + bookToUpdate.getComment()
							+ "', readb = '" + bookToUpdate.getRead()
							+ "' WHERE id = " + bookToUpdate.getId() + ";");

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes.";
			InfoError.showMessage(errorText);

			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum Löschen eines Datensatzes aus der Tabelle "books"
	 * 
	 * @param bookID
	 * @return successful
	 */
	public static int deleteBook(String bookID) {

		try {
			// Datenbankverbindung herstellen
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("DELETE FROM book_database.books WHERE id = "
							+ bookID + ";");

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Löschen eines Datensatzes.";
			InfoError.showMessage(errorText);
			
			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum Schließen aller offenen Verbindungen
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
			
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Verbindungen konnten nicht geschlossen werden.";
			InfoError.showMessage(errorText);
		}
	}
}