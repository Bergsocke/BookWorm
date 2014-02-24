package view.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.book.BookDB;
import model.user.User;

/**
 * Die Klasse "BookGUI" ist für den Aufbau der grafischen Oberfläche zuständig.
 * Hier werden die einzelnen Komponenten des Fensters (Textfelder, ComboBoxen,
 * Bezeichungsfelder, Buttons und die Büchertabelle) festgelegt.
 * 
 * @author Bergsocke
 * 
 */

public class BookGUI extends JFrame {

	private static final long serialVersionUID = -4071792935538021823L;

	// Festlegung der Schriftart für die Bezeichnungsfelder und Buttons
	private static String labelFont = "Verdana";
	private static int labelStyle = Font.BOLD;
	private static int labelSize = 12;

	// Festlegung der Schriftart für die Textfelder und der Tabelle
	private static String textFont = "Arial";
	private static int textStyle = Font.PLAIN;
	private static int textSize = 12;

	// Deklaration der erforderlichen Komponenten
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel westPanel;

	private JLabel searchLabel;
	private JTextField searchText;
	private JButton searchButton;
	private JComboBox<String> searchCombo;

	private JButton allButton;

	private JLabel bookIdLabel;
	private JTextField bookIdText;

	private JLabel isbnLabel;
	private JTextField isbnText;

	private JLabel titleLabel;
	private JTextField titleText;

	private JLabel authorLabel;
	private JTextField authorText;

	private JLabel formatLabel;
	private JComboBox<String> formatCombo;

	private JLabel publicationDateLabel;
	private JTextField publicationDateText;

	private JLabel shortDescriptionLabel;
	private JTextArea shortDescriptionArea;

	private JLabel categoryLabel;
	private JComboBox<String> categoryCombo;

	private JLabel commentLabel;
	private JTextArea commentArea;

	private JLabel readLabel;
	private JComboBox<String> readCombo;

	private JButton clearButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton closeButton;

	private JLabel necessary;

	private JTable bookTable;

	private JScrollPane tableScroll;
	private JScrollPane shortDescriptionScroll;
	private JScrollPane commentScroll;

	private JMenuBar bookMenuBar;
	private JMenu changeMenu;
	private JMenuItem changeMenuItem;
	private JMenu logoutMenu;
	private JMenuItem logoutMenuItem;
	private JMenuItem closeMenuItem;
	private JMenu helpMenu;
	private JMenuItem helpMenuItem;

	private User loginUser;

	/**
	 * Konstruktur (Fensterbeschriftung und Initialisierung der Komponenten)
	 * 
	 * @param frameTitle
	 * @param loginUser
	 */
	public BookGUI(String frameTitle, User loginUser) {

		super(frameTitle);

		this.loginUser = loginUser;

		// Initialisierung der Fenster-Komponenten
		this.initComponents();
	}

	/**
	 * Bücherverwaltung-GUI wird aufgebaut
	 * 
	 * @param loginUser
	 */
	public static void letStartedBookGUI(User loginUser) {

		// Aufruf des Konstruktors der Klasse BookGUI und Zuweisung der
		// Überschrift
		BookGUI gui = new BookGUI("BOOKWORM - BÜCHERVERWALTUNG", loginUser);

		// Fenstergröße wird automatisch an den Inhalt angepasst
		gui.pack();

		// Fenstergröße soll nicht verändert werden können
		gui.setResizable(false);

		// Positionierung am Desktop
		gui.setLocation(100, 150);

		// Window-Close-Funktion
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Fenster wird auf sichtbar gesetzt
		gui.setVisible(true);
	}

	/**
	 * Eine Menüleiste und der Inhalt der einzelnen JPanels (North, East, West)
	 * werden zugewiesen
	 */
	private void initComponents() {

		// MenüBar wird erzeugt
		this.initMenuBar();

		// North-, East-, und WestPanels werden erzeugt
		this.initComponentsNorth();
		this.initComponentsEast();
		this.initComponentsWest();
	}

	/**
	 * Zuweisung des Inhalts der einzelnen Komponenten, die in der MenüBar
	 * ausgegeben werden.
	 */
	private void initMenuBar() {

		bookMenuBar = new JMenuBar();

		ActionListener myActionListener = new BookGUIActionListener(this,
				loginUser);

		changeMenu = new JMenu("Wechseln");
		changeMenuItem = new JMenuItem("Zur Userverwaltung wechseln");
		changeMenu.add(changeMenuItem);
		changeMenuItem.addActionListener(myActionListener);
		// Da nur Administratoren Zugriff auf die Userverwaltung haben
		// sollen, wird abgefragt, ob der angemeldete Anwender die Rolle
		// "Administrator" hat. Wenn ja, wird das MenuItem
		// "Zur Userverwaltung wechseln" aktiv gesetzt
		if (loginUser.getUserRole().contains("Administrator")) {
			changeMenu.setEnabled(true);
		} else {
			changeMenu.setVisible(false);
		}

		logoutMenu = new JMenu("Abmelden");
		logoutMenuItem = new JMenuItem("Benutzer abmelden");
		closeMenuItem = new JMenuItem("Programm beenden");
		logoutMenu.add(logoutMenuItem);
		logoutMenu.add(closeMenuItem);
		logoutMenuItem.addActionListener(myActionListener);
		closeMenuItem.addActionListener(myActionListener);

		helpMenu = new JMenu("Hilfe");
		helpMenuItem = new JMenuItem("Über das Programm");
		helpMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(myActionListener);

		// Hinzufügen der einzelnen Komponenten zur Menübar
		bookMenuBar.add(changeMenu);
		bookMenuBar.add(logoutMenu);
		bookMenuBar.add(helpMenu);

		// Hinzufügen der Menübar zum Frame
		this.setJMenuBar(bookMenuBar);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * North-Panel ausgegeben werden. Mit der Möglichkeit zur Suche nach einem
	 * Buchtitel bzw. zur Anzeige aller Datensätze
	 */
	private void initComponentsNorth() {

		northPanel = new JPanel();

		ActionListener myActionListener = new BookGUIActionListener(this,
				loginUser);

		searchLabel = new JLabel("Suchkriterium auswählen ");
		searchLabel.setFont(new Font(textFont, labelStyle, textSize));

		searchCombo = new JComboBox<String>();
		searchCombo.setBackground(Color.white);
		searchCombo.setFont(new Font(textFont, textStyle, textSize));
		// Festlegung des Inhalts der Combo-Box "searchCombo"
		String[] search = { "Buchtitel", "Autor", "Kategorie", "ISBN" };
		for (int i = 0; i < search.length; i++) {
			searchCombo.addItem(search[i]);
		}
		searchCombo.addActionListener(myActionListener);

		searchText = new JTextField("Bitte Suchbegriff eingeben", 20);
		searchText.setFont(new Font(textFont, textStyle, textSize));
		searchText.setSelectionStart(0);
		searchText.setSelectionEnd(30);

		// Icon für den Buttton "suchen"
		final Icon searchIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/searchIcon.png"));
		searchButton = new JButton("suchen  ", searchIcon);
		searchButton.setFont(new Font(labelFont, labelStyle, labelSize));
		searchButton.setBackground(Color.lightGray);
		searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
		searchButton.addActionListener(myActionListener);

		// Icon für den Buttton "alle anzeigen"
		final Icon showAllIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/showAllIcon.png"));
		allButton = new JButton(" alle anzeigen  ", showAllIcon);
		allButton.setFont(new Font(labelFont, labelStyle, labelSize));
		allButton.setBackground(Color.lightGray);
		allButton.setBorder(BorderFactory.createRaisedBevelBorder());
		allButton.addActionListener(myActionListener);

		// Hinzufügen der einzelnen Komponenten zum NorthPanel
		northPanel.add(searchLabel);
		northPanel.add(searchCombo);
		northPanel.add(searchText);
		northPanel.add(searchButton);
		northPanel.add(allButton);

		// Hinzufügen des NorthPanel zum Frame
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * East-Panel ausgegeben werden. Felder für die Ausgabe, Bearbeitung,
	 * Neuerfassung und Löschen einzelner Bücher
	 */
	private void initComponentsEast() {

		eastPanel = new JPanel();

		// Layout für 15 Zeilen und 2 Spalten
		eastPanel.setLayout(new GridLayout(15, 2));

		// Unsichtbarer Rahmen wird gesetzt, um Abstand zum Frame zu bekommen
		eastPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 15));

		ActionListener myActionListener = new BookGUIActionListener(this,
				loginUser);

		bookIdLabel = new JLabel("Buch-ID: ");
		bookIdLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		// Festlegung der Länge des Textfeldes, die anderen Textfelder werden
		// dann ebenfalls an diese Größe angepasst
		bookIdText = new JTextField(20);
		bookIdText.setFont(new Font(textFont, textStyle, textSize));

		// Nachdem die Buch-ID automatisch von der Datenbank vergeben wird, soll
		// das Feld "Buch-ID" nicht bearbeitet werden können. Aus diesem Grund
		// wird es auf nicht editierbar gesetzt. Aus diesem Feld wird beim
		// Updaten, Speichern oder Löschen eines Datensatzes die Buch-ID
		// ausgelesen
		bookIdText.setEditable(false);

		isbnLabel = new JLabel("ISBN-Nummer: ");
		isbnLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		isbnText = new JTextField();
		isbnText.setFont(new Font(textFont, textStyle, textSize));

		titleLabel = new JLabel("Buch-Titel: *");
		titleLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		titleText = new JTextField();
		titleText.setFont(new Font(textFont, textStyle, textSize));

		authorLabel = new JLabel("Autor: (NN, VN) ");
		authorLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		authorText = new JTextField();
		authorText.setFont(new Font(textFont, textStyle, textSize));

		publicationDateLabel = new JLabel("Ausgabe-Jahr: ");
		publicationDateLabel
				.setFont(new Font(labelFont, labelStyle, labelSize));
		publicationDateText = new JTextField();
		publicationDateText.setFont(new Font(textFont, textStyle, textSize));

		formatLabel = new JLabel("Buch-Format: ");
		formatLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		formatCombo = new JComboBox<String>();
		formatCombo.setBackground(Color.white);
		formatCombo.setFont(new Font(textFont, textStyle, textSize));
		// Festlegung des Inhalts der Combo-Box "formatCombo"
		String[] format = { "", "Taschenbuch", "Gebunden", "Hörbuch",
				"Elektronisch", "unbekannt" };
		for (int i = 0; i < format.length; i++) {
			formatCombo.addItem(format[i]);
		}

		shortDescriptionLabel = new JLabel("Kurzbeschreibung: ");
		shortDescriptionLabel
				.setFont(new Font(labelFont, labelStyle, labelSize));
		shortDescriptionArea = new JTextArea("");
		shortDescriptionArea.setFont(new Font(textFont, textStyle, textSize));
		// Anzeigenumbruch soll stattfinden
		shortDescriptionArea.setLineWrap(true);
		// Festlegung, dass der Anzeigenumbruch nur bei Leerzeichen stattfinden
		// soll (nicht innerhalb eines Wortes)
		shortDescriptionArea.setWrapStyleWord(true);
		// Scrollbar soll zur Verfügung stehen
		shortDescriptionScroll = new JScrollPane(shortDescriptionArea);

		categoryLabel = new JLabel("Kategorie: ");
		categoryLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		categoryCombo = new JComboBox<String>();
		categoryCombo.setBackground(Color.white);
		categoryCombo.setFont(new Font(textFont, textStyle, textSize));
		// Festlegung des Inhalts der Combo-Box "categoryCombo"
		String[] category = { "", "Roman", "Sachbuch", "Sonstiges" };
		for (int i = 0; i < category.length; i++) {
			categoryCombo.addItem(category[i]);
		}

		commentLabel = new JLabel("Kommentar: ");
		commentLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		commentArea = new JTextArea("");
		commentArea.setFont(new Font(textFont, textStyle, textSize));
		// Anzeigenumbruch soll stattfinden
		commentArea.setLineWrap(true);
		// Festlegung, dass der Anzeigenumbruch nur bei Leerzeichen stattfinden
		// soll (nicht innerhalb eines Worte
		commentArea.setWrapStyleWord(true);
		// Scrollbar soll zur Verfügung stehen
		commentScroll = new JScrollPane(commentArea);

		readLabel = new JLabel("Gelesen: ");
		readLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		readCombo = new JComboBox<String>();
		readCombo.setBackground(Color.white);
		readCombo.setFont(new Font(textFont, textStyle, textSize));
		// Festlegung des Inhalts der Combo-Box "readCombo"
		String[] read = { "", "Ja", "Nein" };
		for (int i = 0; i < read.length; i++) {
			readCombo.addItem(read[i]);
		}

		// Icon für den Buttton "Neu"
		final Icon newIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/newIcon.png"));
		clearButton = new JButton("     Neu", newIcon);
		clearButton.setFont(new Font(labelFont, labelStyle, labelSize));
		clearButton.setBackground(Color.lightGray);
		clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// Wenn auf den Button "neu" geklickt wird, soll der Inhalt der
		// Textfelder im EastPanel zurückgesetzt werden. Ein neuer Datensatz
		// wird erst beim Klick auf den Button "speichern" in die Datenbank
		// eingefügt. Der Button "löschen" soll deaktiviert werden.
		clearButton.addActionListener(myActionListener);

		// Icon für den Buttton "speichern"
		final Icon saveIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/saveIcon.png"));
		saveButton = new JButton("     speichern", saveIcon);
		saveButton.setFont(new Font(labelFont, labelStyle, labelSize));
		saveButton.setBackground(Color.lightGray);
		saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "speichern" geklickt wird, soll der Datensatz in
		// die Datenbank gespeichert werden
		saveButton.addActionListener(myActionListener);

		// Icon für den Buttton "löschen"
		final Icon deleteIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/deleteIcon.png"));
		deleteButton = new JButton("     löschen", deleteIcon);
		deleteButton.setFont(new Font(labelFont, labelStyle, labelSize));
		deleteButton.setBackground(Color.lightGray);
		deleteButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "löschen" geklickt wird, soll der Datensatz
		// aus der Datenbank gelöscht werden
		deleteButton.addActionListener(myActionListener);
		// Der Button "löschen" ist zu Beginn/beim Erscheinen des Fensters
		// noch nicht auswählbar; er wird erst sichtbar, wenn ein Datensatz
		// ausgewählt wurde
		deleteButton.setEnabled(false);

		// Icon für den Buttton "Programm beenden"
		final Icon closeIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/closeIcon.png"));
		closeButton = new JButton(" Programm beenden", closeIcon);
		closeButton.setFont(new Font(labelFont, labelStyle, labelSize));
		closeButton.setBackground(Color.lightGray);
		closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "Programm beenden" geklickt wird, soll das
		// Fenster geschlossen werden
		closeButton.addActionListener(myActionListener);

		// Dummy-Labels für Abstand zwischen der Tabelle und den Buttons
		JLabel dummyLabel = new JLabel();
		JLabel dummyLabel2 = new JLabel();
		// Dummy-Labels für Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		JLabel dummyLabel3 = new JLabel();
		JLabel dummyLabel4 = new JLabel();

		necessary = new JLabel("* Pflichteingabe");
		necessary.setFont(new Font(labelFont, labelStyle, 10));

		// Hinzufügen der einzelnen Komponenten zum EastPanel
		eastPanel.add(bookIdLabel);
		eastPanel.add(bookIdText);

		eastPanel.add(isbnLabel);
		eastPanel.add(isbnText);

		eastPanel.add(titleLabel);
		eastPanel.add(titleText);

		eastPanel.add(authorLabel);
		eastPanel.add(authorText);

		eastPanel.add(publicationDateLabel);
		eastPanel.add(publicationDateText);

		eastPanel.add(formatLabel);
		eastPanel.add(formatCombo);

		eastPanel.add(shortDescriptionLabel);
		eastPanel.add(shortDescriptionScroll);

		eastPanel.add(categoryLabel);
		eastPanel.add(categoryCombo);

		eastPanel.add(commentLabel);
		eastPanel.add(commentScroll);

		eastPanel.add(readLabel);
		eastPanel.add(readCombo);

		// Dummy-Labels für Abstand zwischen der Tabelle und den Buttons
		eastPanel.add(dummyLabel);
		eastPanel.add(dummyLabel2);

		eastPanel.add(clearButton);
		eastPanel.add(saveButton);
		eastPanel.add(deleteButton);
		eastPanel.add(closeButton);

		eastPanel.add(necessary);

		// Dummy-Labels für Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		eastPanel.add(dummyLabel3);
		eastPanel.add(dummyLabel4);

		// Hinzufügen des EastPanel zum Fenster
		this.getContentPane().add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * West-Panel ausgegeben werden.
	 */
	private void initComponentsWest() {
		// Initailisierung der Büchertabelle. Nachdem auch von anderen
		// Teilen des Programms auf die Tabelle zugegriffen wird bzw.
		// die Tabelle für die Suchfunktion neu aufgebaut werden muss,
		// wird dieser Teil in eine eigene Methode geschrieben.

		createBookTable();
	}

	/**
	 * Aufbau der Büchertabelle (diese wird im WestPanel ausgegeben)
	 * 
	 * @return bookTable
	 */
	public JTable createBookTable() {

		// Datenbankverbindung
		BookDB myBookDB = new BookDB();

		// Bei der erstmaligen Initialisierung des Fensters gibt es noch keine
		// Darstellungsprobleme mit der Tabelle. Es werden alle Datensätze
		// angezeigt
		if (getSearchText().getText().matches("Bitte Suchbegriff eingeben")) {
			// alle Datensätze werden angezeigt
			bookTable = new JTable(new BookTable(myBookDB.displayAll()));

		} else {
			// Damit bei Eingabe eines Suchbegriffes die Tabelle neu aufgebaut
			// wird, wird sie zunächst einmal aus dem WestPanel entfernt, danach
			// wird nach dem entsprechenden Suchbegriff gesucht und am Ende
			// werden die Suchergebnisse in einer neu erstellten Tabelle
			// angezeigt

			this.getContentPane().remove(westPanel);

			// Einlesen des Suchkriteriums (Buchtitel, Autor, Kategorie, ISBN)
			String searchKey = String.valueOf(this.getSearchCombo()
					.getSelectedItem());

			bookTable = new JTable(new BookTable(myBookDB.findBook(searchKey,
					getSearchText().getText())));
		}

		// WestPanel wird aufgebaut
		this.createteWestTable();

		return bookTable;
	}

	/**
	 * WestPanel wird aufgebaut. Weiters wird das Layout der Tabelle im
	 * WestPanel festgelegt.
	 */
	private void createteWestTable() {

		westPanel = new JPanel();

		// Falls die Spalten zu breit für den verfügbaren Platz sind,
		// soll eine Scrollbar zur Verfügung stehen
		tableScroll = new JScrollPane(bookTable);
		// Festlegung der Tabellengröße
		tableScroll.setPreferredSize(new Dimension(890, 550));
		// dem WestPanel zuweisen
		westPanel.add(tableScroll);

		// Hinzufügen des WestPanel zum Fenster
		this.getContentPane().add(westPanel, BorderLayout.WEST);
		// Sichtbar machen
		this.setVisible(true);

		// Die automatische Größenausrichtung der Spaltenbreite der Tabelle wird
		// ausgeschalten. Ansonsten kann eine bestimmte Spaltengröße nicht
		// festgelegt werden; die Spaltenbreite würde sonst automatisch nach dem
		// verfügbaren Platz festgelegt werden
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Autosortierung wird aktiviert
		bookTable.setAutoCreateRowSorter(true);

		// Festlegung der Schrifteigenschaften
		bookTable.setFont(new Font(textFont, textStyle, textSize));

		// Festlegung der Zeilenhöhe
		bookTable.setRowHeight(20);

		// Festlegung der Spaltenbreiten
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		bookTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		bookTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		bookTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		bookTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		bookTable.getColumnModel().getColumn(5).setPreferredWidth(90);
		bookTable.getColumnModel().getColumn(6).setPreferredWidth(200);
		bookTable.getColumnModel().getColumn(7).setPreferredWidth(70);
		bookTable.getColumnModel().getColumn(8).setPreferredWidth(200);
		bookTable.getColumnModel().getColumn(9).setPreferredWidth(70);

		// Festlegung des Selektionsmodus - es darf nur eine Zeile ausgewählt
		// werden
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Hinzufügen des BookTableListener. Dadurch ist es möglich die
		// Selektion des Users abzufangen und den entsprechenden Datensatz in
		// den Textfeldern des EastPanels anzuzeigen
		bookTable.getSelectionModel().addListSelectionListener(
				new BookTableListener(this));
	}

	/**
	 * Wird ein neuer Datensatz in die Datenbank gespeichert, wird die Tabelle
	 * im WestPanel neu aufgebaut, damit der neue Datensatz gleich angezeigt
	 * wird
	 */
	public void reloadWestTable() {

		this.getContentPane().remove(westPanel);
		bookTable = new JTable(new BookTable(new BookDB().displayAll()));

		// WestPanel aufbauen
		this.createteWestTable();
	}

	/**
	 * Der Inhalt aller Tabellenfelder im EastPanel wird zurückgesetzt
	 */
	public void resetTableEast() {

		this.getBookIdText().setText("");
		this.getIsbnText().setText("");
		this.getTitleText().setText("");
		this.getAuthorText().setText("");
		this.getPublicationDateText().setText("");
		this.getFormatCombo().setSelectedItem("");
		this.getShortDescriptionArea().setText("");
		this.getCategoryCombo().setSelectedItem("");
		this.getCommentArea().setText("");
		this.getReadCombo().setSelectedItem("");
	}

	/**
	 * Definition der Getter und Setter
	 */
	public JTextField getBookIdText() {
		return bookIdText;
	}

	public void setBookIdText(JTextField bookIdText) {
		this.bookIdText = bookIdText;
	}

	public JTextField getIsbnText() {
		return isbnText;
	}

	public void setIsbnText(JTextField isbnText) {
		this.isbnText = isbnText;
	}

	public JTextField getTitleText() {
		return titleText;
	}

	public void setTitleText(JTextField titleText) {
		this.titleText = titleText;
	}

	public JTextField getAuthorText() {
		return authorText;
	}

	public void setAuthorText(JTextField authorText) {
		this.authorText = authorText;
	}

	public JComboBox<String> getFormatCombo() {
		return formatCombo;
	}

	public void setFormatCombo(JComboBox<String> formatCombo) {
		this.formatCombo = formatCombo;
	}

	public JTextField getPublicationDateText() {
		return publicationDateText;
	}

	public void setPublicationDateText(JTextField publicationDateText) {
		this.publicationDateText = publicationDateText;
	}

	public JTextArea getShortDescriptionArea() {
		return shortDescriptionArea;
	}

	public void setShortDescriptionArea(JTextArea shortDescriptionArea) {
		this.shortDescriptionArea = shortDescriptionArea;
	}

	public JComboBox<String> getCategoryCombo() {
		return categoryCombo;
	}

	public void setCategoryCombo(JComboBox<String> categoryCombo) {
		this.categoryCombo = categoryCombo;
	}

	public JTextArea getCommentArea() {
		return commentArea;
	}

	public void setCommentArea(JTextArea commentArea) {
		this.commentArea = commentArea;
	}

	public JComboBox<String> getReadCombo() {
		return readCombo;
	}

	public void setReadCombo(JComboBox<String> readCombo) {
		this.readCombo = readCombo;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JTextField getSearchText() {
		return searchText;
	}

	public JComboBox<String> getSearchCombo() {
		return searchCombo;
	}

	public void setSearchCombo(JComboBox<String> searchCombo) {
		this.searchCombo = searchCombo;
	}

	public void setSearchText(JTextField searchText) {
		this.searchText = searchText;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	public JButton getAllButton() {
		return allButton;
	}

	public void setAllButton(JButton allButton) {
		this.allButton = allButton;
	}

	public JButton getNewButton() {
		return clearButton;
	}

	public void setNewButton(JButton newButton) {
		this.clearButton = newButton;
	}

	public JTable getBookTable() {
		return bookTable;
	}

	public void setBookTable(JTable bookTable) {
		this.bookTable = bookTable;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
}
