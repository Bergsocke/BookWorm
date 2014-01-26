package a_loginPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

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
	 * Diese Methode baut die Datenbankverbindung zur Datenbank
	 * "book_management" auf
	 */
	public static void connectDB() {

		try {
			// Treiber wird geladen und die Regestrierung beim DriverManager
			// erfolgt
			Class.forName("com.mysql.jdbc.Driver");

			// DriverManager wird verwenden und die Verbindung zur DB wird
			// aufgebaut
			connect = DriverManager.getConnection(
					"jdbc:mysql://localhost/book_database", "book_user",
					"book_password");

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankverbindung konnte nicht hergestellt werden. Bitte prüfen Sie, "
							+ "ob der MySQL-Server läuft.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Diese Methode prüft, ob die angegebene Benutzername/Password-Kombination
	 * in der Tabelle "users" vorhanden ist
	 * 
	 * @param username
	 * @param userpassword
	 * @return numRows
	 */
	public static int login(LoginUser myUser) {
		// Variable für Anzahl der gefundenen Datensätze
		int numRows = 0;

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT COUNT(*) FROM book_database.users WHERE username = '"
							+ myUser.getUsername()
							+ "' AND userpassword = '"
							+ myUser.getUserpassword() + "';");

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

			// Anzahl der Datensätze ermitteln
			while (myResultSet.next()) {
				numRows = myResultSet.getInt("count(*)");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankabfrage konnte nicht durchgeführt werden.",
					"Fehler", JOptionPane.ERROR_MESSAGE);

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
			JOptionPane.showMessageDialog(null,
					"Verbindungen konnten nicht geschlossen werden.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}