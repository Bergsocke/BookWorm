package view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.user.User;
import view.InfoError;

/**
 * Mit der Klasse "LoginActionListener" werden die Aktionen für die Buttons
 * "Login" und "Abbrechen" der Klasse LoginGUI festgelegt.
 * 
 * @author Bergsocke
 * 
 */
public class LoginGUIActionListener implements ActionListener {

	private LoginGUI guiLogin;

	/**
	 * Konstruktor
	 * 
	 * @param guiLogin
	 */
	public LoginGUIActionListener(LoginGUI guiLogin) {
		this.guiLogin = guiLogin;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// Wenn auf den Button "Abbrechen" geklickt wird, wird das Login-Fenster
		// geschlossen
		if (event.getActionCommand().contains("Abbrechen")) {
			guiLogin.closeLoginDialog();
		}

		// Wenn auf den Button "Login" geklickt wird, wird die Anmeldung
		// versucht
		if (event.getActionCommand().contains("Login")) {
			// Der eingegebene Benutzername und das eingegebene Password werden
			// eingelesen
			User myUser = new User(String.valueOf(guiLogin.getUsernameText()
					.getText()), String.valueOf(guiLogin.getPasswordText()
					.getPassword()));

			// Password wird mit MD5 gehasht
			String hashPassword = passwordHash(myUser);

			// gehashtes Password wird gesetzt
			myUser.setUserPassword(hashPassword);

			// Anmeldung wird versucht
			guiLogin.startLogin(myUser);
		}
	}

	public static String passwordHash(User myUser) {

		String algorithm = "md5";
		String password = myUser.getUserPassword();
		String hashPassword = "";

		MessageDigest myMessageDigest;
		try {
			// Algorithmus, der die Berechnungsfunktion implementiert
			// (in diesem Fall md5)
			myMessageDigest = MessageDigest.getInstance(algorithm);
			// Hashwert wird berechnet
			byte[] digest = myMessageDigest.digest(password.getBytes());

			// Hashwert wird einem String zugewiesen
			for (byte b : digest)
				// "%02x" -> konvertiert Bytes in Hexadecimal
				hashPassword += String.format("%02x", b);

		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Das eingegebene Password konnte nicht gehasht werden.";
			InfoError.showMessage(errorText);
		}

		return hashPassword;
	}
}
