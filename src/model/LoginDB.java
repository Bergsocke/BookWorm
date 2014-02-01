package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import view.InfoError;

/**
 * Die Klasse "LoginDB" stellte eine Verbindung zur MySQL-Datenbank
 * "book_management" her und prüft, ob die eingegebene
 * Benutzername/Password-Kombination in der Tabelle "users" vorhanden ist
 * 
 * @author Bergsocke
 * 
 */
public class LoginDB {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;


	/**
	 * Diese Methode prüft, ob die angegebene Benutzername/Password-Kombination
	 * in der Tabelle "users" vorhanden ist
	 * 
	 * @param username
	 * @param userpassword
	 * @return numRows
	 */
	public static int login(User myUser) {
		// Variable für Anzahl der gefundenen Datensätze
		int numRows = 0;

		try {
			// Datenbankverbindung herstellen
			connect = ConnectionDatabase.connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT COUNT(*) FROM book_database.users WHERE username = '"
							+ myUser.getUserName()
							+ "' AND userpassword = '"
							+ myUser.getUserPassword() + "';");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

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
			closeConnections();
		}

		return numRows;
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