package model.login;

import java.sql.ResultSet;

import model.SQLDatabase;
import model.user.User;
import view.InfoError;

/**
 * Die Klasse "LoginDB" prüft, ob die eingegebene
 * Benutzername/Password-Kombination in der Tabelle "users" vorhanden ist
 * 
 * @author Bergsocke
 * 
 */
public class LoginDB {

	private static ResultSet myResultSet = null;


	/**
	 * Diese Methode prüft, ob die angegebene Benutzername/Password-Kombination
	 * in der Tabelle "users" vorhanden ist
	 * 
	 * @param myUser
	 * @return numRows
	 */
	public static int login(User myUser) {
		// Variable für Anzahl der gefundenen Datensätze
		int numRows = 0;

		try {
			// PreparedStatement für den SQL-Befehl
			String sqlStatement = "SELECT COUNT(*) FROM bookworm_database.users WHERE username = '"
					+ myUser.getUserName()
					+ "' AND userpassword = '"
					+ myUser.getUserPassword() + "';";

			// SQL-Befehl wird ausgeführt
			myResultSet = SQLDatabase.executeSQLQuery(sqlStatement);

			// Anzahl der Datensätze ermitteln
			while (myResultSet.next()) {
				numRows = myResultSet.getInt("count(*)");
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

		return numRows;
	}
}