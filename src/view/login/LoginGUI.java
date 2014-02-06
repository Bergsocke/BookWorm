package view.login;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.SQLDatabase;
import model.login.LoginDB;
import model.user.User;
import view.InfoError;
import view.book.BookGUI;

/**
 * Die Klasse "LoginGUI" enthält die Main-Methode zum Starten der
 * Java-Applikation BookWorm.
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
public class LoginGUI extends JDialog implements KeyListener {

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
		LoginGUI gui = new LoginGUI(loginFrame);

		// Überschrift
		gui.setTitle("Anmeldung BookWorm");

		// Fenstergröße wird gesetzt
		gui.setSize(400, 300);

		// Positionierung am Desktop
		gui.setLocation(600, 300);

		// Sichtbar machen
		gui.setVisible(true);
	}

	/**
	 * Initialisierung des Login-Panels und Zuweisung von Inhalt und Form der
	 * einzelnen Komponenten, die dann im Panel ausgegeben werden
	 */
	private void initLoginGUI() {

		loginPanel = new JPanel();

		loginPanel.setLayout(null);

		ActionListener myActionListener = new LoginGUIActionListener(this);

		usernameLabel = new JLabel("Benutzername:");
		usernameLabel.setBounds(49, 90, 100, 30);
		usernameText = new JTextField();
		usernameText.setBounds(165, 95, 100, 20);
		// Wenn die Enter-Taste geklickt wird, wird der Button "Login" ausgelöst
		usernameText.addKeyListener(this);

		passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(51, 125, 100, 30);
		passwordText = new JPasswordField();
		passwordText.setBounds(164, 128, 100, 20);
		// Wenn die Enter-Taste geklickt wird, wird der Button "Login" ausgelöst
		passwordText.addKeyListener(this);

		loginButton = new JButton("Login");
		loginButton.setBounds(96, 172, 109, 30);
		// Wenn auf den Button "Login" geklickt wird, soll die Anmeldung
		// erfolgen
		loginButton.addActionListener(myActionListener);

		breakButton = new JButton("Abbrechen");
		breakButton.setBounds(217, 171, 109, 30);
		// Wenn auf den Button "Abbrechen" geklickt wird, soll das Login-Fenster
		// geschlossen werden
		breakButton.addActionListener(myActionListener);

		// Hinzufügen der einzelnen Komponenten zum Panel
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
	 * führt, wenn die Daten korrekt sind, den Start der Bücherverwaltung durch
	 * 
	 * @param myUser
	 */
	public void startLogin(User myUser) {
		// es wird geprüft, ob die angegebene Benutzername/Password-Kombination
		// in der Tabelle "users" vorhanden ist
		int numRow = LoginDB.login(myUser);

		// Wenn die Benutzerdaten korrekt sind, wird die Bücherverwaltung
		// gestartet
		if (numRow == 1) {

			// es werden die kompletten Anwenderdaten eingelesen (ID, Name,
			// Password, Rolle)
			User loginUser = LoginDB.loginuser(myUser);

			// Die Bücherverwaltung wird gestartet
			BookGUI.letStartedBookGUI(loginUser);

			// Offene Datenbank-Verbindungen werden geschlossen
			SQLDatabase.closeConnections();

			// Login-Fenster wird geschlossen
			this.closeLoginDialog();

		} else {
			// Sind die eingegebenen Benutzerdaten nicht korrekt, wird eine
			// entsprechende Meldung ausgegeben
			String errorText = "Benutzername oder Passwort ist falsch";
			InfoError.showMessage(errorText);

			// Benutzername und Passwort-Feld werden zurückgesetzt
			this.getUsernameText().setText("");
			this.getPasswordText().setText("");
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
	 * KeyActionListener
	 * 
	 * wird die Enter-Taste gedrückt, soll der Button "Login" ausgelöst werden
	 */
	@Override
	public void keyPressed(KeyEvent event) {
		// Wenn die Taste "Enter" gedrückt wird, wird die Anmeldung versucht
		if (KeyEvent.VK_ENTER == event.getKeyCode()) {
			loginButton.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

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
