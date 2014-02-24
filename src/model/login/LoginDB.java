package model.login;

import java.sql.ResultSet;

import model.SQLDatabase;
import model.user.User;
import view.InfoError;

/**
 * Die Klasse "LoginDB" prüft, ob die vom Anwender eingegebene
 * Benutzername/Password-Kombination in der Tabelle "users" vorhanden ist
 * 
 * @author Bergsocke
 * 
 */
public class LoginDB {

	private ResultSet myResultSet = null;
	// Datenbankverbindung
	private SQLDatabase mySQLDatabase = new SQLDatabase();

	/**
	 * Diese Methode prüft, ob die eingegebene Benutzername/Password-Kombination
	 * in der Tabelle "users" vorhanden ist
	 * 
	 * @param loginUser
	 * @return numRows
	 */
	public int login(User loginUser) {
		// Variable für Anzahl der gefundenen Datensätze
		int numRows = 0;

		try {
			// PreparedStatement für den SQL-Befehl
			String sqlStatement = "SELECT COUNT(*) FROM bookworm_database.users WHERE username = '"
					+ loginUser.getUserName()
					+ "' AND userpassword = '"
					+ loginUser.getUserPassword() + "';";

			// SQL-Befehl wird ausgeführt
			myResultSet = mySQLDatabase.executeSQLQuery(sqlStatement);

			// Anzahl der Datensätze ermitteln
			while (myResultSet.next()) {
				numRows = myResultSet.getInt("count(*)");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage konnte nicht durchgeführt werden.";
			new InfoError().showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}

		return numRows;
	}

	/**
	 * Diese Methode liest die kompletten Daten des Anwenders aus (ID, Name,
	 * Passwort, Rolle)
	 * 
	 * @param loginUser
	 * @return foundUser
	 */
	public User loginuser(User loginUser) {
		User foundUser = null;

		try {
			// PreparedStatement für den SQL-Befehl
			String sqlStatement = "SELECT * FROM bookworm_database.users WHERE username = '"
					+ loginUser.getUserName()
					+ "' AND userpassword = '"
					+ loginUser.getUserPassword() + "';";

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
			new InfoError().showMessage(errorText);

		} finally {
			// offene Verbindungen werden geschlossen
			mySQLDatabase.closeConnections();
		}

		return foundUser;
	}
}