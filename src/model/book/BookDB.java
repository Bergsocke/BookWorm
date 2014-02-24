package model.book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.SQLDatabase;
import view.InfoError;

/**
 * Die Klasse "BookDB" bietet die Methoden zum Anzeigen, Suchen, Speichern und
 * Löschen von Datensätzen der Tabelle "books"
 * 
 * @author Bergsocke
 * 
 */
public class BookDB {

	private static ResultSet myResultSet = null;
	// Variable, die anzeigen soll, ob das Speichern, Updaten oder Löschen eines
	// Datensatzes erfolgreich war
	private static int successful = 0;
	// Dialogfenster
	private static InfoError errorMessage = new InfoError();

	/**
	 * Es werden alle Datensätze, die in der Tabelle "books" vorhanden sind,
	 * angezeigt
	 * 
	 * @return bookList
	 */
	public static List<Book> displayAll() {

		List<Book> bookList = new ArrayList<Book>();

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.books;";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

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
			errorMessage.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}

		return bookList;
	}

	/**
	 * Methode zum Suchen nach einem bestimmten Suchkriterium (Autor, Buchtitel,
	 * Kategorie, ISBN) in der Tabelle "books"
	 * 
	 * @param searchKey
	 * @param searchText
	 * @return bookList
	 */
	public static List<Book> findBook(String searchKey, String searchText) {

		List<Book> bookList = new ArrayList<Book>();
		String key = "";

		// SQL-Tabellenspalte
		if (searchKey == "Autor") {
			key = "author";
		} else if (searchKey == "Buchtitel") {
			key = "title";
		} else if (searchKey == "Kategorie") {
			key = "category";
		} else if (searchKey == "ISBN") {
			key = "isbn";
		}

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.books WHERE "
					+ key + " LIKE '%" + searchText + "%';";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

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
			errorMessage.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}

		return bookList;
	}

	/**
	 * Methode zum Suchen nach der Buch-ID in der Tabelle "books"
	 * 
	 * @param bookID
	 * @return foundBook
	 */
	public static Book findByID(String bookID) {

		Book foundBook = null;

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.books WHERE id LIKE "
					+ bookID + ";";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

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
			errorMessage.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
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
			// Erforderlicher SQL-Befehl
			String sqlStatement = "INSERT INTO bookworm_database.books (isbn, title, author, "
					+ "publicationDate, formatb, shortDescription, category, "
					+ "commentb, readb) VALUES ('"
					+ bookToSave.getIsbn()
					+ "', '"
					+ bookToSave.getTitle()
					+ "', '"
					+ bookToSave.getAuthor()
					+ "', '"
					+ bookToSave.getPublicationDate()
					+ "', '"
					+ bookToSave.getFormat()
					+ "', '"
					+ bookToSave.getShortDescription()
					+ "', '"
					+ bookToSave.getCategory()
					+ "', '"
					+ bookToSave.getComment()
					+ "', '"
					+ bookToSave.getRead()
					+ "');";

			// SQL-Befehl wird ausgeführt
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Abspeichern eines Datensatzes.";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
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
			// Erforderlicher SQL-Befehl
			String sqlStatement = "UPDATE bookworm_database.books SET isbn = '"
					+ bookToUpdate.getIsbn() + "', title = '"
					+ bookToUpdate.getTitle() + "', author = '"
					+ bookToUpdate.getAuthor() + "', publicationDate = '"
					+ bookToUpdate.getPublicationDate() + "', formatb = '"
					+ bookToUpdate.getFormat() + "', shortDescription = '"
					+ bookToUpdate.getShortDescription() + "', category = '"
					+ bookToUpdate.getCategory() + "', commentb = '"
					+ bookToUpdate.getComment() + "', readb = '"
					+ bookToUpdate.getRead() + "' WHERE id = "
					+ bookToUpdate.getId() + ";";

			// SQL-Befehl wird ausgeführt
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes.";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
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
			// Erforderlicher SQL-Befehl
			String sqlStatement = "DELETE FROM bookworm_database.books WHERE id = "
					+ bookID + ";";

			// SQL-Befehl wird ausgeführt
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Löschen eines Datensatzes.";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}
	}
}