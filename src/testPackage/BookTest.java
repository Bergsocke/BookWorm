package testPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import databasePackage.ConnectionDatabase;

/**
 * Klasse mit Main-Methode zum Testen (Consolenausgabe)
 * 
 */
public class BookTest {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;

	/**
	 * Main-Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		displayAllTest();
	}

	/**
	 * Es werden alle vorhandenen Datensätze der Tabelle "books" angezeigt
	 */
	public static void displayAllTest() {
		try {
			connect = ConnectionDatabase.connectDB();

			// Alle Datensätze anzeigen
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.books;");
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				int id = myResultSet.getInt(1);
				String isbn = myResultSet.getString(2);
				String title = myResultSet.getString(3);
				String author = myResultSet.getString(4);
				String publicationDate = myResultSet.getString(5);
				String format = myResultSet.getString(6);
				String shortDescription = myResultSet.getString(7);
				String comment = myResultSet.getString(8);
				String read = myResultSet.getString(9);

				System.out.println(id + ": " + isbn + ", " + title + ", "
						+ author + ", " + publicationDate + ", " + format + ", "
						+ shortDescription + ", " + comment + ", " + read);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
