package testPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bookDatabasePackage.BookDB;

/**
 * Die Klasse "BookWormTest" dient zum Testen der Funktionalitäten
 * (Consolenausgabe)
 */

public class BookWormTest {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;

	/**
	 * Main-Methode zum Starten
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Verbindung zur Datenbank herstellen
		connect = BookDB.connectDB();
		// Alle vorhandenen Datensätze anzeigen
		displayAllBooks();
	}

	/**
	 * Zeigt alle Datensätze an, die in der Tabelle "books" vorhanden sind und
	 * gibt diese auf der Console aus
	 */
	public static void displayAllBooks() {

		try {
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books;");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {

				int id = myResultSet.getInt(1);
				String isbn = myResultSet.getString(2);
				String title = myResultSet.getString(3);
				String author = myResultSet.getString(4);
				String publicationDate = myResultSet.getString(5);
				String shortDescription = myResultSet.getString(6);
				String comment = myResultSet.getString(7);
				String format = myResultSet.getString(8);
				String read = myResultSet.getString(9);

				System.out.println(id + ": " + " " + isbn + ", " + title + ", "
						+ author + ", " + publicationDate + ", "
						+ shortDescription + ", " + " " + comment + ", " + ", "
						+ format + ", " + ", " + read);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
