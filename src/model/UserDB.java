package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import view.InfoError;

/**
 * Die Klasse "UserDB" stellte eine Verbindung zur MySQL-Datenbank
 * "book_database" her und bietet die Methoden zum Anzeigen, Suchen, Speichern
 * und Löschen von Datensätzen der Tabelle "users"
 * 
 * @author Bergsocke
 * 
 */

public class UserDB {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;
	// Variable, die anzeigen soll, ob das Speichern, Updaten oder Löschen eines
	// Datensatzes erfolgreich war
	public static int successful = 0;

	/**
	 * Es werden alle Datensätze, die in der Tabelle "users" vorhanden sind,
	 * angezeigt
	 * 
	 * @return userList
	 */
	public static List<User> displayAll() {

		List<User> userList = new ArrayList<User>();

		try {
			// Datenbankverbindung herstellen
			connect = SQLDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.users;");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

			while (myResultSet.next()) {
				userList.add(new User(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3)));
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

		return userList;
	}

	/**
	 * Methode zum Suchen nach der User-ID in der Tabelle "users"
	 * 
	 * @param userID
	 * @return selectedBook
	 */
	public static User findByID(String userID) {

		User foundUser = null;

		try {
			// Datenbankverbindung herstellen
			connect = SQLDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT * FROM book_database.users WHERE id LIKE "
							+ userID + ";");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

			// da das Select-Statement immer nur genau einen oder keinen
			// Datensatz liefern kann, genügt hier diese Abfrage
			if (myResultSet.next()) {
				foundUser = new User(myResultSet.getString(1),
						myResultSet.getString(2), myResultSet.getString(3));
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

		return foundUser;
	}

	/**
	 * Methode zum Speichern eines neuen Datensatzes in die Tabelle "users"
	 * 
	 * @param userToSave
	 * @return successful
	 */
	public static int saveBook(User userToSave) {

		try {
			// Datenbankverbindung herstellen
			connect = SQLDatabase.connectDB();

			// PreparedStatement für SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("INSERT INTO book_database.users VALUES('"
							+ userToSave.getUserName()
							+ "', md5('"
							+ userToSave.getUserPassword() + "'));");

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Abspeichern eines Datensatzes";
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
	 * "users"
	 * 
	 * @param userToUpdate
	 * @return successful
	 */
	public static int updateBook(User userToUpdate) {

		try {
			// Datenbankverbindung herstellen
			connect = SQLDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("UPDATE book_database.users SET username = '"
							+ userToUpdate.getUserName()
							+ "', password = md5('"
							+ userToUpdate.getUserPassword() + "'));");

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes";
			InfoError.showMessage(errorText);

			successful = 0;
			closeConnections();
			return successful;

		} finally {
			closeConnections();
		}
	}

	/**
	 * Methode zum Löschen eines Datensatzes aus der Tabelle "users"
	 * 
	 * @param userID
	 * @return successful
	 */
	public static int deleteBook(String userID) {

		try {
			// Datenbankverbindung herstellen
			connect = SQLDatabase.connectDB();

			// PreparedStatement für SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("DELETE FROM book_database.users WHERE id = "
							+ userID + ";");

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate();

			// offene Verbindungen werden geschlossen
			closeConnections();

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Löschen eines Datensatzes";
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
			String errorText = "Verbindungen konnten nicht geschlossen werden";
			InfoError.showMessage(errorText);
		}
	}

}
