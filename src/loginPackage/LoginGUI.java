package loginPackage;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import viewPackage.BookGUI;

/**
 * Die Klasse "LoginGUI" enthält die Main-Methode zum Starten der
 * Java-Applikation.
 * 
 * Ein Login-Fenster wird erzeugt, in dem der Benutzer seinen Benutzernamen und
 * sein Passwort eingeben kann. Sind die eingegebenen Daten korrekt, wird die
 * Bücherverwaltungs-GUI aufgebaut. Sind die eingegebenen Daten nicht korrekt,
 * wird eine entsprechende Meldung ausgegeben und der Benutzer kann erneut seine
 * Daten eingeben.
 * 
 * @author Bergsocke
 * 
 */
public class LoginGUI extends JDialog {

	private static final long serialVersionUID = -6035816178550912787L;

	// Deklaration der erforderlichen Komponenten
	private JPanel loginPanel;

	private JButton loginButton;
	private JButton breakButton;

	private JLabel usernameLabel;
	private JTextField usernameText;

	private JLabel passwordLabel;
	private JPasswordField passwordText;

	/**
	 * Die Main-Methode zum Starten der Java-Applikation
	 * 
	 * Ein Anmeldefenster wird erzeugt, in dem der Benutzer seine Benutzerdaten
	 * eingeben kann
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Ein Frame wird erzeugt
		JFrame loginFrame = new JFrame();

		// Konstruktor wird aufgerufen und ein Frame zugewiesen
		LoginGUI myLogin = new LoginGUI(loginFrame);

		// �berschrift
		myLogin.setTitle("Anmeldung f�r die Bücherverwaltung");

		// Fenstergröße wird gesetzt
		myLogin.setSize(400, 300);

		// Positionierung am Desktop
		myLogin.setLocation(600, 300);

		// Sichtbar machen
		myLogin.setVisible(true);
	}

	/**
	 * Konstruktor
	 * 
	 * @param loginFrame
	 */
	public LoginGUI(JFrame loginFrame) {
		super(loginFrame);
		// Aufbau des Login-Fensters
		initLoginGUI();
	}

	/**
	 * Initialisierung des Login-Panels und Zuweisung von Inhalt und Form der
	 * einzelnen Komponenten, die dann im Panel ausgegeben werden
	 */
	private void initLoginGUI() {

		loginPanel = new JPanel();

		loginPanel.setLayout(null);

		usernameLabel = new JLabel("Benutzername:");
		usernameLabel.setBounds(49, 90, 100, 30);
		usernameText = new JTextField();
		usernameText.setBounds(165, 95, 100, 20);

		passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(51, 125, 100, 30);
		passwordText = new JPasswordField();
		passwordText.setBounds(164, 128, 100, 20);

		loginButton = new JButton("Login");
		loginButton.setBounds(96, 172, 109, 30);
		// Wenn auf den Button "Login" geklickt wird, soll die Anmeldung
		// erfolgen
		loginButton.addActionListener(new LoginActionListener(this));

		breakButton = new JButton("Abbrechen");
		breakButton.setBounds(217, 171, 109, 30);
		// Wenn auf den Button "Abbrechen" geklickt wird, soll das Login-Fenster
		// geschlossen werden
		breakButton.addActionListener(new LoginActionListener(this));

		// Hinzuf�gen der einzelnen Komponenten zum Panel
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameText);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordText);
		loginPanel.add(loginButton);
		loginPanel.add(breakButton);

		// Panel wird dem Frame zugewiesen
		this.getContentPane().add(loginPanel, BorderLayout.CENTER);
	}

	/**
	 * Diese Methode startet die Prüfung, ob die Benutzerdaten korrekt sind und
	 * führt gegebenenfalls den Start der Bücherverwaltung durch
	 * 
	 * @param myUser
	 */
	public void startLogin(LoginUser myUser) {

		int numRow = LoginDB.login(myUser);
		// Benutzerdaten sind korrekt
		if (numRow == 1) {
			// Die B�cherverwaltung wird gestartet
			BookGUI.letStarted();

			// Offene Datenbank-Verbindungen werden geschlossen
			LoginDB.closeConnections();

			// Login-Fenster wird geschlossen
			this.closeLoginDialog();

		} else {
			// Sind die eingegebenen Benutzerdaten nicht korrekt, wird eine
			// entsprechende Meldung ausgegeben
			JOptionPane.showMessageDialog(this,
					"Benutzername oder Passwort ist falsch", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Setzt das Login-Fenster auf invisible (bei erfolgreicher Anmeldung oder
	 * wenn der Button "Abbrechen" geklickt wurde)
	 */
	public void closeLoginDialog() {
		this.setVisible(false);
	}

	/**
	 * Definition der Getter und Setter
	 */
	public JTextField getUsernameText() {
		return usernameText;
	}

	public void setUsernameText(JTextField usernameText) {
		this.usernameText = usernameText;
	}

	public JPasswordField getPasswordText() {
		return passwordText;
	}

	public void setPasswordText(JPasswordField passwordText) {
		this.passwordText = passwordText;
	}

}
