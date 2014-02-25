package model.user;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.SQLDatabase;
import view.InfoError;

/**
 * Die Klasse "UserDB" bietet die Methoden zum Anzeigen, Suchen, Speichern und
 * Löschen von Datensätzen der Tabelle "users"
 * 
 * @author Bergsocke
 * 
 */

public class UserDB {

	private ResultSet myResultSet = null;
	// Variable, die anzeigen soll, ob das Speichern, Updaten oder Löschen eines
	// Datensatzes erfolgreich war
	private int successful = 0;
	// Dialogfenster
	private InfoError errorMessage = new InfoError();
	// Datenbankverbindung
	private SQLDatabase mySQLDatabase = new SQLDatabase();

	/**
	 * Es werden alle Datensätze, die in der Tabelle "users" vorhanden sind,
	 * angezeigt
	 * 
	 * @return userList
	 */
	public List<User> displayAll() {

		List<User> userList = new ArrayList<User>();

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users;";

			// SQL-Befehl wird ausgeführt
			myResultSet = mySQLDatabase.executeSQLQuery(sqlStatement);

			while (myResultSet.next()) {
				userList.add(new User(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			errorMessage.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}

		return userList;
	}

	/**
	 * Methode zum Suchen nach der User-ID in der Tabelle "users"
	 * 
	 * @param userID
	 * @return foundUser
	 */
	public User findByID(String userID) {

		User foundUser = null;

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users WHERE id LIKE "
					+ userID + ";";

			// SQL-Befehl wird ausgeführt
			myResultSet = mySQLDatabase.executeSQLQuery(sqlStatement);

			// da das Select-Statement immer nur genau einen oder keinen
			// Datensatz liefern kann, genügt hier diese Abfrage
			if (myResultSet.next()) {
				foundUser = new User(myResultSet.getString(1),
						myResultSet.getString(2), myResultSet.getString(3),
						myResultSet.getString(4));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			errorMessage.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}

		return foundUser;
	}

	/**
	 * Methode zum Suchen nach einem bestimmten Suchkriterium (Anwender, Rolle)
	 * in der Tabelle "Users
	 * 
	 * @param searchKey
	 * @param searchText
	 * @return userList
	 */
	public List<User> findUser(String searchKey, String searchText) {

		List<User> userList = new ArrayList<User>();
		String key = "";

		// SQL-Tabellenspalte
		if (searchKey == "Anwender") {
			key = "username";
		} else if (searchKey == "Rolle") {
			key = "userrole";
		}

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users WHERE "
					+ key + " LIKE '%" + searchText + "%';";

			// SQL-Befehl wird ausgeführt
			myResultSet = mySQLDatabase.executeSQLQuery(sqlStatement);

			while (myResultSet.next()) {
				userList.add(new User(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			errorMessage.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}

		return userList;
	}

	/**
	 * Methode zum Speichern eines neuen Datensatzes in die Tabelle "users"
	 * 
	 * @param userToSave
	 * @return successful
	 */
	public int saveUser(User userToSave) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "INSERT INTO bookworm_database.users VALUES (default, '"
					+ userToSave.getUserName()
					+ "', md5('"
					+ userToSave.getUserPassword()
					+ "'), '"
					+ userToSave.getUserRole() + "');";

			// SQL-Befehl wird ausgeführt
			successful = mySQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Abspeichern eines Datensatzes";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}
	}

	/**
	 * Methode zum Ändern eines bereits vorhandenen Datensatzes in der Tabelle
	 * "users" (das Passwort bleibt unverändert)
	 * 
	 * @param userToUpdate
	 * @return successful
	 */
	public int updateUser(User userToUpdate) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "UPDATE bookworm_database.users SET username = '"
					+ userToUpdate.getUserName()
					+ "', userrole = '"
					+ userToUpdate.getUserRole()
					+ "' WHERE id ="
					+ userToUpdate.getUserID() + ";";

			// SQL-Befehl wird ausgeführt
			successful = mySQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}
	}

	/**
	 * Methode zum Ändern eines bereits vorhandenen Datensatzes in der Tabelle
	 * "users" inklusive eines neuen Passworts
	 * 
	 * @param userToUpdate
	 * @return successful
	 */
	public int newPassword(User userToUpdate) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "UPDATE bookworm_database.users SET username = '"
					+ userToUpdate.getUserName()
					+ "', userpassword = md5('"
					+ userToUpdate.getUserPassword()
					+ "')"
					+ ", userrole = '"
					+ userToUpdate.getUserRole()
					+ "' WHERE id ="
					+ userToUpdate.getUserID() + ";";
			;

			// SQL-Befehl wird ausgeführt
			successful = mySQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}
	}

	/**
	 * Methode zum Löschen eines Datensatzes aus der Tabelle "users"
	 * 
	 * @param userID
	 * @return successful
	 */
	public int deleteUser(String userID) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "DELETE FROM bookworm_database.users WHERE id = "
					+ userID + ";";

			// SQL-Befehl wird ausgeführt
			successful = mySQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Löschen eines Datensatzes";
			errorMessage.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}
	}
}
