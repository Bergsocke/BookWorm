package a_loginPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

/**
 * Die Klasse "LoginDB" stellte eine Verbindung zur MySQL-Datenbank
 * "book_management" her und pr�ft, ob die eingegebene
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
					"Datenbankverbindung konnte nicht hergestellt werden. Bitte pr�fen Sie, "
							+ "ob der MySQL-Server l�uft.", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Diese Methode pr�ft, ob die angegebene Benutzername/Password-Kombination
	 * in der Tabelle "users" vorhanden ist
	 * 
	 * @param username
	 * @param userpassword
	 * @return numRows
	 */
	public static int login(LoginUser myUser) {
		// Variable f�r Anzahl der gefundenen Datens�tze
		int numRows = 0;

		try {
			// Datenbankverbindung herstellen
			connectDB();

			// PreparedStatement f�r den SQL-Befehl
			myPreparedStatement = connect
					.prepareStatement("SELECT COUNT(*) FROM book_database.users WHERE username = '"
							+ myUser.getUsername()
							+ "' AND userpassword = '"
							+ myUser.getUserpassword() + "';");

			// SQL-Befehl wird ausgef�hrt
			myResultSet = myPreparedStatement.executeQuery();

			// Anzahl der Datens�tze ermitteln
			while (myResultSet.next()) {
				numRows = myResultSet.getInt("count(*)");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			JOptionPane.showMessageDialog(null,
					"Datenbankabfrage konnte nicht durchgef�hrt werden.",
					"Fehler", JOptionPane.ERROR_MESSAGE);

		} finally {
			// offene Verbindungen werden geschlossen
			closeConnections();
		}

		return numRows;
	}

	/**
	 * Methode zum Schlie�en aller offenen Verbindungen
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