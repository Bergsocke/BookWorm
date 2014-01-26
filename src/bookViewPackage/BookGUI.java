package bookViewPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

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

import bookDatabasePackage.BookDB;

/**
 * Die Klasse "BookGUI" ist f�r den Aufbau der grafischen Oberfl�che zust�ndig.
 * Hier werden die einzelnen Komponenten des Fensters (Textfelder, ComboBoxen,
 * Bezeichungsfelder, Schaltfl�chen und die B�chertabelle) festgelegt.
 * 
 * @author Bergsocke
 * 
 */

public class BookGUI extends JFrame {

	private static final long serialVersionUID = -4071792935538021823L;

	// Festlegung der Schriftart f�r die Bezeichnungsfelder und Schaltfl�chen
	static String labelFont = "Verdana";
	static int labelStyle = Font.BOLD;
	static int labelSize = 12;

	// Festlegung der Schriftart f�r die Textfelder und der Tabelle
	static String textFont = "Arial";
	static int textStyle = Font.PLAIN;
	static int textSize = 12;

	// Deklaration der erforderlichen Komponenten
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel westPanel;

	private JLabel searchLabel;
	private JTextField searchText;
	private JButton searchButton;

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

	private JLabel commentLabel;
	private JTextArea commentArea;

	private JLabel readLabel;
	private JComboBox<String> readCombo;

	private JButton clearButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton closeButton;

	private JTable bookTable;

	private JScrollPane tableScroll;
	private JScrollPane shortDescriptionScroll;
	private JScrollPane commentScroll;

	private JMenuBar bookMenuBar;
	private JMenu clearMenu;
	private JMenuItem clearMenuItem;
	private JMenu saveMenu;
	private JMenuItem saveMenuItem;
	private JMenu deleteMenu;
	private JMenuItem deleteMenuItem;
	private JMenu helpMenu;
	private JMenuItem helpMenuItem;

	public static void letStarted() {

		// Aufruf des Konstruktors der Klasse BookGUI und Zuweisung der
		// �berschrift
		BookGUI gui = new BookGUI("B�CHERVERWALTUNG");

		// Fenstergr��e wird automatisch an den Inhalt angepasst
		gui.pack();

		// Fenstergr��e soll nicht ver�ndert werden k�nnen
		gui.setResizable(false);

		// Positionierung am Desktop
		gui.setLocation(200, 300);

		// Window-Close-Funktion
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Fenster wird auf sichtbar gesetzt
		gui.setVisible(true);
	}

	/**
	 * Konstruktur (Fensterbeschriftung und Initialisierung der Komponenten)
	 * 
	 * @param frameTitle
	 */
	public BookGUI(String frameTitle) {

		super(frameTitle);

		// Initialisierung der Fenster-Komponenten
		this.initComponents();
	}

	/**
	 * Eine Men�leiste und der Inhalt der einzelnen JPanels (North, East, West)
	 * werden zugewiesen
	 */
	private void initComponents() {

		// MenuBar wird erzeugt
		this.initMenuBar();

		// North- East und WestPanels werden erzeugt
		this.initComponentsNorth();
		this.initComponentsEast();
		this.initComponentsWest();
	}

	/**
	 * Zuweisung des Inhalts der einzelnen Komponenten, die in der Men�Bar
	 * ausgegeben werden.
	 */
	public void initMenuBar() {

		bookMenuBar = new JMenuBar();

		clearMenu = new JMenu("Neu");
		clearMenuItem = new JMenuItem("Neuen Datensatz anlegen");
		clearMenu.add(clearMenuItem);
		clearMenuItem.addActionListener(new BookGUIActionListener(this));

		saveMenu = new JMenu("Speichern");
		saveMenuItem = new JMenuItem("Datensatz speichern");
		saveMenu.add(saveMenuItem);
		saveMenuItem.addActionListener(new BookGUIActionListener(this));

		deleteMenu = new JMenu("L�schen");
		deleteMenuItem = new JMenuItem("Ausgew�hlten Datensatz l�schen");
		deleteMenu.add(deleteMenuItem);
		deleteMenuItem.addActionListener(new BookGUIActionListener(this));

		helpMenu = new JMenu("Hilfe");
		helpMenuItem = new JMenuItem("�ber das Programm");
		helpMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(new BookGUIActionListener(this));

		// Hinzuf�gen der einzelnen Komponenten zur Men�bar
		bookMenuBar.add(clearMenu);
		bookMenuBar.add(saveMenu);
		bookMenuBar.add(deleteMenu);
		bookMenuBar.add(helpMenu);

		// Hinzuf�gen der Men�bar zum Frame
		this.setJMenuBar(bookMenuBar);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * North-Panel ausgegeben werden. Mit der M�glichkeit zur Suche nach einem
	 * Buchtitel bzw. zur Anzeige aller Datens�tze
	 */
	private void initComponentsNorth() {

		northPanel = new JPanel();

		searchLabel = new JLabel("Nach Buchtitel suchen: ");
		searchLabel.setFont(new Font(textFont, labelStyle, 14));

		searchText = new JTextField("Bitte Suchbegriff eingeben", 20);
		searchText.setFont(new Font(textFont, textStyle, textSize));
		searchText.setSelectionStart(0);
		searchText.setSelectionEnd(30);

		// Icon f�r den Buttton "suchen"
		final Icon searchIcon = new ImageIcon(
				BookGUI.class
						.getResource("/bookViewPackage/images/searchIcon.png"));
		searchButton = new JButton("suchen  ", searchIcon);
		searchButton.setFont(new Font(labelFont, labelStyle, labelSize));
		searchButton.setBackground(Color.lightGray);
		searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
		searchButton.addActionListener(new BookGUIActionListener(this));

		// Icon f�r den Buttton "alle anzeigen"
		final Icon showAllIcon = new ImageIcon(
				BookGUI.class
						.getResource("/bookViewPackage/images/showAllIcon.png"));
		allButton = new JButton(" alle anzeigen  ", showAllIcon);
		allButton.setFont(new Font(labelFont, labelStyle, labelSize));
		allButton.setBackground(Color.lightGray);
		allButton.setBorder(BorderFactory.createRaisedBevelBorder());
		allButton.addActionListener(new BookGUIActionListener(this));

		// Hinzuf�gen der einzelnen Komponenten zum NorthPanel
		northPanel.add(searchLabel);
		northPanel.add(searchText);
		northPanel.add(searchButton);
		northPanel.add(allButton);

		// Hinzuf�gen des NorthPanel zum Frame
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * East-Panel ausgegeben werden. Felder f�r die Ausgabe, Bearbeitung,
	 * Neuerfassung und L�schen einzelner B�cher
	 */
	private void initComponentsEast() {

		eastPanel = new JPanel();

		// Layout f�r 13 Zeilen und 2 Spalten
		eastPanel.setLayout(new GridLayout(13, 2));

		// Unsichtbarer Rahmen wird gesetzt, um Abstand zum Frame zu bekommen
		eastPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 15));

		bookIdLabel = new JLabel("Buch-ID: ");
		bookIdLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		// Festlegung der L�nge des Textfeldes, die anderen Textfelder werden
		// dann ebenfalls an diese Gr��e angepasst
		bookIdText = new JTextField(20);
		bookIdText.setFont(new Font(textFont, textStyle, textSize));

		// Nachdem die Buch-ID automatisch von der Datenbank vergeben wird, soll
		// das Feld "Buch-ID" nicht bearbeitet werden k�nnen. Aus diesem Grund
		// wird es auf nicht editierbar gesetzt. Aus diesem Feld wird beim
		// Updaten, Speichern oder L�schen eines Datensatzes die Buch-ID
		// ausgelesen
		bookIdText.setEditable(false);

		isbnLabel = new JLabel("ISBN-Nummer: ");
		isbnLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		isbnText = new JTextField();
		isbnText.setFont(new Font(textFont, textStyle, textSize));

		titleLabel = new JLabel("Buch-Titel: ");
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
		String[] format = { "", "Taschenbuch", "Gebundene Ausgabe", "Hoerbuch",
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
		// Scrollbar soll zur Verf�gung stehen
		shortDescriptionScroll = new JScrollPane(shortDescriptionArea);

		commentLabel = new JLabel("Kommentar: ");
		commentLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		commentArea = new JTextArea("");
		commentArea.setFont(new Font(textFont, textStyle, textSize));
		// Anzeigenumbruch soll stattfinden
		commentArea.setLineWrap(true);
		// Festlegung, dass der Anzeigenumbruch nur bei Leerzeichen stattfinden
		// soll (nicht innerhalb eines Worte
		commentArea.setWrapStyleWord(true);
		// Scrollbar soll zur Verf�gung stehen
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

		// Icon f�r den Buttton "neu"
		final Icon newIcon = new ImageIcon(
				BookGUI.class
						.getResource("/bookViewPackage/images/newIcon.png"));
		clearButton = new JButton("     neu", newIcon);
		clearButton.setFont(new Font(labelFont, labelStyle, labelSize));
		clearButton.setBackground(Color.lightGray);
		clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// Wenn auf den Button "neu" geklickt wird, soll der Inhalt der
		// Textfelder im EastPanel zur�ckgesetzt werden. Ein neuer Datensatz
		// wird erst beim Klick auf den Button "speichern" in die Datenbank
		// eingef�gt. Der Button "l�schen" soll deaktiviert werden.
		clearButton.addActionListener(new BookGUIActionListener(this));

		// Icon f�r den Buttton "speichern"
		final Icon saveIcon = new ImageIcon(
				BookGUI.class
						.getResource("/bookViewPackage/images/saveIcon.png"));
		saveButton = new JButton("     speichern", saveIcon);
		saveButton.setFont(new Font(labelFont, labelStyle, labelSize));
		saveButton.setBackground(Color.lightGray);
		saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "speichern" geklickt wird, soll der Datensatz in
		// die Datenbank gespeichert werden
		saveButton.addActionListener(new BookGUIActionListener(this));

		// Icon f�r den Buttton "l�schen"
		final Icon deleteIcon = new ImageIcon(
				BookGUI.class
						.getResource("/bookViewPackage/images/deleteIcon.png"));
		deleteButton = new JButton("     l�schen", deleteIcon);
		deleteButton.setFont(new Font(labelFont, labelStyle, labelSize));
		deleteButton.setBackground(Color.lightGray);
		deleteButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "l�schen" geklickt wird, soll der Datensatz
		// aus der Datenbank gel�scht werden
		deleteButton.addActionListener(new BookGUIActionListener(this));
		// Der Button "l�schen" ist zu Beginn/beim Erscheinen des Fensters
		// noch ncht ausw�hlbar; er wird erst sichtbar, wenn ein Datensatz
		// ausgew�hlt wurde
		deleteButton.setEnabled(false);

		// Icon f�r den Buttton "Programm beenden"
		final Icon closeIcon = new ImageIcon(
				BookGUI.class
						.getResource("/bookViewPackage/images/closeIcon.png"));
		closeButton = new JButton(" Programm beenden", closeIcon);
		closeButton.setFont(new Font(labelFont, labelStyle, labelSize));
		closeButton.setBackground(Color.lightGray);
		closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "Programm beenden" geklickt wird, soll das
		// Fenster geschlossen werden
		closeButton.addActionListener(new BookGUIActionListener(this));

		// Dummy-Labels f�r Abstand zwischen der Tabelle und den Buttons
		JLabel dummyLabel = new JLabel();
		JLabel dummyLabel2 = new JLabel();
		// Dummy-Labels f�r Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		JLabel dummyLabel3 = new JLabel();
		JLabel dummyLabel4 = new JLabel();

		// Hinzuf�gen der einzelnen Komponenten zum EastPanel
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

		eastPanel.add(commentLabel);
		eastPanel.add(commentScroll);

		eastPanel.add(readLabel);
		eastPanel.add(readCombo);

		// Dummy-Labels f�r Abstand zwischen der Tabelle und den Buttons
		eastPanel.add(dummyLabel);
		eastPanel.add(dummyLabel2);

		eastPanel.add(clearButton);
		eastPanel.add(saveButton);
		eastPanel.add(deleteButton);
		eastPanel.add(closeButton);

		// Dummy-Labels f�r Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		eastPanel.add(dummyLabel3);
		eastPanel.add(dummyLabel4);

		// Hinzuf�gen des EastPanel zum Fenster
		this.getContentPane().add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * West-Panel ausgegeben werden.
	 */
	private void initComponentsWest() {
		// Initailisierung der B�chertabelle. Nachdem auch von anderen
		// Teilen des Programms auf die Tabelle zugegriffen wird bzw.
		// die Tabelle f�r die Suchfunktion neu aufgebaut werden muss,
		// wird dieser Teil in eine eigene Methode geschrieben.

		createBookTable();
	}

	/**
	 * Aufbau der B�chertabelle (diese wird im WestPanel ausgegeben)
	 * 
	 * @return bookTable
	 */
	public JTable createBookTable() {

		// Bei der erstmaligen Initialisierung des Fensters gibt es noch keine
		// Darstellungsprobleme mit der Tabelle. Es werden alle Datens�tze
		// angezeigt
		if (getSearchText().getText().matches("Bitte Suchbegriff eingeben")) {
			// alle Datens�tze werden angezeigt
			bookTable = new JTable(new BookTable(BookDB.displayAll()));

		} else {
			// Damit bei Eingabe eines Suchbegriffes die Tabelle neu aufgebaut
			// wird, wird sie zun�chst einmal aus dem WestPanel entfernt, danach
			// wird nach dem entsprechenden Suchbegriff gesucht (im
			// Datenbankfeld "title") und am Ende werden die Suchergebnisse
			// in einer neu erstellten Tabelle angezeigt

			this.getContentPane().remove(westPanel);
			bookTable = new JTable(new BookTable(
					BookDB.findByTitle(getSearchText().getText())));
		}

		// WestPanel wird aufgebaut
		this.createteWestTable();

		return bookTable;
	}

	/**
	 * WestPanel wird aufgebaut. Weiters wird das Layout der Tabelle im
	 * WestPanel festgelegt.
	 */
	public void createteWestTable() {

		westPanel = new JPanel();

		// Falls die Spalten zu breit f�r den verf�gbaren Platz sind,
		// soll eine Scrollbar zur Verf�gung stehen
		tableScroll = new JScrollPane(bookTable);
		westPanel.add(tableScroll);

		// Hinzuf�gen des WestPanel zum Fenster
		this.getContentPane().add(westPanel, BorderLayout.WEST);
		// Sichtbar machen
		this.setVisible(true);

		// Die automatische Gr��enausrichtung der Spaltenbreite der Tabelle wird
		// ausgeschalten. Ansonsten kann eine bestimmte Spaltengr��e nicht
		// festgelegt werden; die Spaltenbreite w�rde sonst automatisch nach dem
		// verf�gbaren Platz festgelegt werden
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Festlegung der Schrifteigenschaften
		bookTable.setFont(new Font(textFont, textStyle, textSize));

		// Festlegung der Zeilenh�he
		bookTable.setRowHeight(20);

		// Festlegung der Spaltenbreiten
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		bookTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		bookTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		bookTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		bookTable.getColumnModel().getColumn(4).setPreferredWidth(40);
		bookTable.getColumnModel().getColumn(5).setPreferredWidth(90);
		bookTable.getColumnModel().getColumn(6).setPreferredWidth(200);
		bookTable.getColumnModel().getColumn(7).setPreferredWidth(200);
		bookTable.getColumnModel().getColumn(8).setPreferredWidth(70);

		// Festlegung des Selektionsmodus - es darf nur eine Zeile ausgew�hlt
		// werden
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Hinzuf�gen des BookTableListener. Dadurch ist es m�glich die
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
		bookTable = new JTable(new BookTable(BookDB.displayAll()));

		// WestPanel aufbauen
		this.createteWestTable();
	}

	/**
	 * Der Inhalt aller Tabellenfelder im EastPanel wird zur�ckgesetzt
	 */
	public void resetTableEast() {

		this.getBookIdText().setText("");
		this.getIsbnText().setText("");
		this.getTitleText().setText("");
		this.getAuthorText().setText("");
		this.getPublicationDateText().setText("");
		this.getFormatCombo().setSelectedItem("");
		this.getShortDescriptionArea().setText("");
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

}
