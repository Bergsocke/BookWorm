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
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users;";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

			while (myResultSet.next()) {
				userList.add(new User(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			InfoError.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}

		return userList;
	}

	/**
	 * Methode zum Suchen nach der User-ID in der Tabelle "users"
	 * 
	 * @param userID
	 * @return foundUser
	 */
	public static User findByID(String userID) {

		User foundUser = null;

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users WHERE id LIKE "
					+ userID + ";";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

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
			InfoError.showMessage(errorText);

		} // finally {
			// offene Verbindungen werden geschlossen
			// SQLDatabase.closeConnections();
		// }

		return foundUser;
	}

	/**
	 * Methode zum Suchen nach dem Usernamen in der Tabelle "Users"
	 * 
	 * @param userName
	 * @return userList
	 */
	public static List<User> findByUserName(String userName) {

		List<User> userList = new ArrayList<User>();

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users WHERE username LIKE '%"
					+ userName + "%';";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

			while (myResultSet.next()) {
				userList.add(new User(myResultSet.getString(1), myResultSet
						.getString(2), myResultSet.getString(3), myResultSet
						.getString(4)));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			InfoError.showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}

		return userList;
	}

	/**
	 * Methode zum Speichern eines neuen Datensatzes in die Tabelle "users"
	 * 
	 * @param userToSave
	 * @return successful
	 */
	public static int saveUser(User userToSave) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "INSERT INTO bookworm_database.users VALUES (default, '"
					+ userToSave.getUserName()
					+ "', md5('"
					+ userToSave.getUserPassword()
					+ "'), '"
					+ userToSave.getUserRole() + "');";

			// SQL-Befehl wird ausgeführt
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Abspeichern eines Datensatzes";
			InfoError.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}
	}

	/**
	 * Methode zum Ändern eines bereits vorhandenen Datensatzes in der Tabelle
	 * "users" (das Passwort bleibt unverändert)
	 * 
	 * @param userToUpdate
	 * @return successful
	 */
	public static int updateUser(User userToUpdate) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "UPDATE bookworm_database.users SET username = '"
					+ userToUpdate.getUserName()
					+ "', userrole = '"
					+ userToUpdate.getUserRole()
					+ "' WHERE id ="
					+ userToUpdate.getUserID() + ";";

			// SQL-Befehl wird ausgeführt
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes";
			InfoError.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}
	}

	/**
	 * Methode zum Ändern eines bereits vorhandenen Datensatzes in der Tabelle
	 * "users" inklusive eines neuen Passworts
	 * 
	 * @param userToUpdate
	 * @return successful
	 */
	public static int newPassword(User userToUpdate) {

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
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Ändern eines Datensatzes";
			InfoError.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}
	}

	/**
	 * Methode zum Löschen eines Datensatzes aus der Tabelle "users"
	 * 
	 * @param userID
	 * @return successful
	 */
	public static int deleteUser(String userID) {

		try {
			// Erforderlicher SQL-Befehl
			String sqlStatement = "DELETE FROM bookworm_database.users WHERE id = "
					+ userID + ";";

			// SQL-Befehl wird ausgeführt
			successful = SQLDatabase.executeSQLUpdate(sqlStatement);

			return successful;

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbank-Fehler beim Löschen eines Datensatzes";
			InfoError.showMessage(errorText);

			successful = 0;
			return successful;

		} finally {
			// offene Verbindungen werden geschlossen
			SQLDatabase.closeConnections();
		}
	}
}
