package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.User;

/**
 * Mit der Klasse "LoginActionListener" werden die Aktionen für die Buttons
 * "Login" und "Abbrechen" der Klasse LoginGUI festgelegt.
 * 
 * @author Bergsocke
 * 
 */
public class LoginGUIActionListener implements ActionListener {

	LoginGUI guiLogin;

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
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Abbrechen")) {

			guiLogin.closeLoginDialog();
		}

		// Wenn auf den Button "Login" geklickt wird, wird die Anmeldung
		// versucht
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Login")) {

			// Benutzername und Password werden eingelesen
			User myUser = new User(String.valueOf(guiLogin
					.getUsernameText().getText()), String.valueOf(guiLogin
					.getPasswordText().getPassword()));

			// Password wird mit MD5 gehasht
			try {
				String algorithm = "md5";
				String password = myUser.getUserPassword();

				// Algorithmus, der die Berechnungsfunktion implementiert
				// (in diesem Fall md5)
				MessageDigest myMessageDigest = MessageDigest
						.getInstance(algorithm);

				// Hashwert wird berechnet
				byte[] digest = myMessageDigest.digest(password.getBytes());

				// Hashwert wird einem String zugewiesen
				String hashPassword = "";
				for (byte b : digest)
					// "%02x" -> konvertiert Bytes in Hexadecimal
					hashPassword += String.format("%02x", b);

				// gehashtes Password wird gesetzt
				myUser.setUserPassword(hashPassword);

			} catch (NoSuchAlgorithmException e) {
				System.out.println(e.toString());
				JOptionPane
						.showMessageDialog(
								guiLogin,
								"Das eingegebene Password konnte nicht gehasht werden.",
								"Fehler", JOptionPane.ERROR_MESSAGE);
			}

			// Anmeldung wird versucht
			guiLogin.startLogin(myUser);
		}
	}
}
