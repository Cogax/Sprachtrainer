package WordTrainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

/**
 * MVC - Controller: Diese Klasse koordiniert den Inhalt der View mit den Daten
 * aus dem Model.
 * 
 * @author Andreas Gyr
 */

public class Controller {
	//Konstanten
	//private final int MAX_SETS = 20; //Maximale Anzahl Wortlisten
	static final double VERSION = 1.0;
	
	// TODO diese Variable sollte aus einer Settingsdatei gelesen werden
	private static String xmlFilename = "db.xml";
	private String configFilePath = "Settings/settings.properties";
    private String xmlConfig = "settings.xml";
	
	// View
	private ViewMainFrame viewMF;
	
	// Models
	private Model model; // Model Hauptobjekt
	private ModelLanguage activeLang; // Temporärer zwischenspeicher für das aktuelle Language Objekt
	private ModelWordpairSet activeWordpairSet; // temporärer zwischenspeicher für das aktuelle WordpairSet Objekt
	
	/**
	 * Das Model und die View m�ssen als Parameter �bergeben werden.
	 * 
	 * @param model
	 * @param viewMainFrame
	 */
	public Controller(Model m, ViewMainFrame v) {
		// Felder definieren
		this.viewMF = v;
		this.model = m;
		
		//Import Model wenn vorhanden
		Model XMLmodel = LibXMLWriter.importModel(xmlFilename);
		if(XMLmodel == null) {
			System.out.println("Model per XML laden nicht m�glich, da keine Datei vorhanden ist!");
			viewMF.getMntmEditWordlist().setEnabled(false);
		} else {
			this.model = XMLmodel;
			System.out.println("LanguageCount direkt nach Import: " + this.model.getLanguages().size());
			try {
				//Setzt aktive Sprache
				setActiveLanguage(this.model.getLanguage(0));
				setActiveWordpairSet(this.model.getLanguage(0).getWordpairSet(0));
			
				//Comboboxen bef�llen
				fillLangComboBox();
				fillWordpairSetComboBox();
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Noch keine Sprache in db.xml vorhanden");
			}
		}
		
		// Setzt alle Listener auf das View Objekt
		//Menubar Listener
		viewMF.getMntmSave().addActionListener(new btnSaveAListener()); // selber Listener wie Button speichern
		viewMF.getMntmImportWordlist().addActionListener(new mntmImportWordlistAListener());
		viewMF.getMntmAbout().addActionListener(new mntmAboutAListener());
		viewMF.getMntmSaveAs().addActionListener(new mntmExportAListener());
		viewMF.getMntmEditWordlist().addActionListener(new mntmEditWordlistAListener());
		viewMF.getMntmNewWordlist().addActionListener(new mntmNewWordlistAListener());
		
		//Button Listener
		viewMF.getBtnImportWordlist().addActionListener(new mntmImportWordlistAListener()); //selber Listener wie Men� Import
		viewMF.getBtnLearn().addActionListener(new btnLearnAListener());
		viewMF.getBtnCheck().addActionListener(new btnCheckAListener());
		viewMF.getBtnNext().addActionListener(new btnNextAListener());
		viewMF.getBtnView().addActionListener(new btnViewAListener());
		viewMF.getBtnSave().addActionListener(new btnSaveAListener());
		
		//ComboBox Listener
		viewMF.getComboBoxSelectLang().addItemListener(new comboBoxSelectLangItemListener());
		viewMF.getComboBoxSelectWordpairSet().addActionListener(new comboBoxSelectWordpairSetAListener());
		
		//Textfeld Listener
		viewMF.getTxtLang2().addKeyListener(new TxtLang2KeyListener());

		// set ViewMainFrame visible
		viewMF.setVisible(true);
	}
	
	/**
	 * Startet mit dem Lernen des aktiven Wordpair
	 * @param String comboBoxValue
	 */
	private void learn(int index) {
		//Setzt Auswahl aus Combobox als aktive Wortliste
		setActiveWordpairSet(this.activeLang.getWordpairSet(index));
		
		//Wort anzeigen
		nextWord();
		
	}
	
	/**
	 * Setzt WordpairSet als aktives Set
	 * @param ModelWordpairSet activeWordpairSet
	 */
	private void setActiveWordpairSet(ModelWordpairSet activeWP) {
		this.activeWordpairSet = activeWP;
		
		//F�llt Sprachlabels ab
		viewMF.getLblLang1().setText(ResourceBundle.getBundle("locale.messages").getString(this.activeLang.getLang1()));
		viewMF.getLblLang2().setText(ResourceBundle.getBundle("locale.messages").getString(this.activeLang.getLang2()));
	}
	
	/**
	 * Setzt Language als aktiv
	 * @param ModelLanguage activeLang
	 */
	private void setActiveLanguage(ModelLanguage activeLang) {
		this.activeLang = activeLang;
	}
	
	/**
	 *	Pr�ft, ob das eingegebene Wort korrekt ist.
	 */
	private void checkWordInput() {
		String word1 = viewMF.getTxtLang1().getText();
		String word2 = viewMF.getTxtLang2().getText();
		//Alle Wordpaare pr�fen, ob eines das angezeigte ist.
		for (ModelWordpair wordpair : this.activeWordpairSet.getWordpairs()) {
			if (wordpair.getWord1().equals(word1)) { //entspricht das angezeigte Word dem aus der Liste?
				if (wordpair.checkIfMatch(word2)) { //entspricht das eingegeben Word dem aus der liste?
					System.out.println("Input correct");
					wordpair.correct(); //Wort als richtig setzen
					viewMF.setCorrectIcon(true);
					nextWord();
				}else {
					System.out.println("Input incorrect");
					wordpair.wrong();//Wort als falsch setzen
					viewMF.setCorrectIcon(false);
				}
			}
		}
	}
	
	/**
	 * Zeigt das n�chste Wort an
	 */
	private void nextWord() {
		//TODO N�chstes Wort aufgrund Faktor ausw�hlen
		String newWord;
		int index;
		viewMF.getTxtLang2().setText(""); //Eingabe l�schen
		
		//Zuf�lliges Wort anzeigen
		Random rand = new Random();
		index = rand.nextInt(activeWordpairSet.getWordpairs().size());
		newWord = activeWordpairSet.getWordpairs().get(index).getWord1();
		viewMF.getTxtLang1().setText(newWord);
	}
	
	/**
	 * Zeigt die L�sung an
	 */
	private void showCorrectWord() {
		String word1 = viewMF.getTxtLang1().getText();
		//Alle Wordpaare pr�fen, ob eines das angezeigte ist.
		for (ModelWordpair wordpair : this.activeWordpairSet.getWordpairs()) {
			if (wordpair.getWord1().equals(word1)) { //entspricht das angezeigte Word dem aus der Liste?
				viewMF.getTxtLang2().setText(wordpair.getWord2());
			}
		}
	}
	
	/**
	 * Sichert den aktuellen Status als XML im Program Ordner
	 */
	private void saveState() {
		 LibXMLWriter.exportModel(xmlFilename, model);
	}
	
	/**
	 * Importiert eine Wortliste und speichert diese in ein Array
	 */
	private void importWordList() {
		try {
			String wordpairSetName;
			ModelLanguage language = null;
			
			JFileChooser fileChooser = new JFileChooser();
			
			// Filter setzten, damit nur CSV Dateien ausgewaehlt werden koennen
			fileChooser.setFileFilter(new LibJFileChooserFilter(".csv"));
			
			// "Alle Dateien" Filter deaktivieren
			fileChooser.setAcceptAllFileFilterUsed(false);
			
			// Dialog zum oeffnen von Dateien anzeigen. Als 1. Parameter wird das uebergeordnete Fenster erwartet,
			// damit dieses gesperrt werden kann
			int open = fileChooser.showDialog(null, ResourceBundle.getBundle("locale.messages", LibGlbSet.getLocale()).getString("importWordlist"));
			
			// Pruefen ob auf oeffnen geklickt wurde
			if(open == JFileChooser.APPROVE_OPTION) {
				/**
				 * CSV Einlesen und WordpairSet / Wordpair Objekte erzeugen
				 */
				LibCsvReader reader = new LibCsvReader(fileChooser.getSelectedFile().getAbsolutePath(), 2, ";");
				ArrayList<String[]> csvData = reader.getData();				
				
				//Key 1 und 2 aus CSV in String speichern
				String langKey1 = csvData.get(0)[0];
				String langKey2 = csvData.get(0)[1];
				
				//Wordpair Bezeichnung abfragen
				if(csvData.get(1)[0].equalsIgnoreCase("name")) { //2. Zeile, 1. Spalte
					wordpairSetName = csvData.get(1)[1]; //2. Zeile, 2. Spalte
				} else {
					// default Name
					wordpairSetName = csvData.get(1)[0] + " - " + csvData.get(1)[1]; //name - bezeichnung
				}
				
				//Erste zwei Zeilen l�schen
				csvData.remove(0); //remove langKey
				csvData.remove(0); //remove name, bezeichnung
				
				// ------------------------------------
				
				// Pr�fen ob schon Sprachen vorhanden sind
				if(model.getLanguageCount() > 0) {
					System.out.println("Es sind schon Sprachen vorhanden");
					
					// Pr�fen ob die Sprache der Importierten Wortliste einer der vorhandenen Sprachen entspricht
					int langIndex = model.getLanguageIndex(langKey1, langKey2);
					if(langIndex >= 0) {
						System.out.println("Die Sprache existiert bereits");
						language = model.getLanguage(langIndex);
					}
				}
				
				// Pr�fen ob eine Sprache geladen wurde
				if(language == null) {
					// Keine Sprache geladen - erstelle eine
					System.out.println("Keine Sprache gefunden, erstelle eine");
					language = new ModelLanguage(langKey1, langKey2);
				}
				
				//Setzt aktive Sprache & f�gt sie dem Model hinzu
				setActiveLanguage(language);
				this.model.addLanguage(language);
				
				// Pr�fen ob die language schon WordpairSets hat
				boolean createWordpairSet = true;
				if(language.getWordpairSetCount() > 0) {
					System.out.println("Es sind bereits WordpairSets in der Sprache vorhanden");
					
					// Pr�fen ob das Importierte WordpairSet schon existiert
					int wordpairSetIndex = language.getWordpairSetIndex(wordpairSetName);
					if(wordpairSetIndex >= 0) {
						System.out.println("Ein WordpairSet mit dem Namen existiert bereits");
						createWordpairSet = false;
					}
				}
				if(createWordpairSet) {
					// WordpairSet erstellen und Wordpairs eintragen
					ModelWordpairSet wordpairSet = new ModelWordpairSet(wordpairSetName);
					for(String[] line : csvData) {
						wordpairSet.addWordpair(new ModelWordpair(line[0], line[1]));
					}
					language.addWordpairSet(wordpairSet);
					setActiveWordpairSet(wordpairSet);
				}
				
				// ----------------------------------------------	
				
				//Comboboxen bef�llen
				fillLangComboBox();
				fillWordpairSetComboBox();

				
				//Learn Button visible setzen
				viewMF.getBtnLearn().setEnabled(true);
				viewMF.getMntmEditWordlist().setEnabled(true);
				
				//checkbox visible setzen
				//viewMF.getChckbxSetAsDefault().setEnabled(true);
			}
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}
	
	/**
	 * F�llt die Combobox mit den Languages
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void fillLangComboBox() {
		JComboBox jBoxSelLang = viewMF.getComboBoxSelectLang();
		jBoxSelLang.removeAllItems(); //Combobox leeren
		
		System.out.println("Languages count: " + model.getLanguageCount());
		
		//Eintr�ge hinzuf�gen
		int i = 0;
		for(ModelLanguage l : model.getLanguages()) {
			
			String lang1, lang2, txt;
			lang1 = ResourceBundle.getBundle("locale.messages").getString(l.getLang1());
			lang2 = ResourceBundle.getBundle("locale.messages").getString(l.getLang2());
			txt = lang1 + " - " + lang2 + " (id:" + i + ")";
			jBoxSelLang.addItem(new ComboItem(i, txt));
			
			i++;
		}

		
		System.out.println("JBox ItemCount:" + jBoxSelLang.getItemCount());
		if (jBoxSelLang.getItemCount() > 0)
			viewMF.getComboBoxSelectLang().setEnabled(true); //JCombobox SelectLang aktivieren
	}
	
	/**
	 * F�llt die Combobox mit den WordlistSets
	 */
	public void fillWordpairSetComboBox() {
		JComboBox jBoxSelWpSet = viewMF.getComboBoxSelectWordpairSet();
		jBoxSelWpSet.removeAllItems(); //Combobox leeren
		
		//Ausgew�hlte Sprache identifizieren
		if (viewMF.getComboBoxSelectLang().getSelectedIndex() >= 0) {
			int i = 0;
			System.out.println("Wordpairsets in der gew�hlen Sprache: " + activeLang.getWordpairSetCount());
			for (ModelWordpairSet mWpSet : activeLang.getWordpairSets()) {
				System.out.println(mWpSet.getName());
				jBoxSelWpSet.addItem(new ComboItem(i, mWpSet.getName()));
				i++;
			}
		}
		
		if (jBoxSelWpSet.getItemCount() > 0)
			jBoxSelWpSet.setEnabled(true);
	}
	
	/*
	 * Start Button Listener Klassen
	 */
	//Button Check: Eingabe pr�fen 
	class btnCheckAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			checkWordInput();
		}
	}
	
	//Button Next: N�chstes Wort anzeigen
	class btnNextAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			viewMF.deleteCorrectIcon();//CorrectIcon l�schen
			nextWord();
		}
	}
	
	//Button View: L�sung anzeigen
	class btnViewAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			viewMF.deleteCorrectIcon();//CorrectIcon l�schen
			showCorrectWord();
		}
	}
	
	//Button Learn: Setzt Alle Elemente Visible startet learn Methode
	class btnLearnAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(viewMF.getComboBoxSelectWordpairSet().getItemCount() > 0) {
				//alle Felder visible setzen
				viewMF.getLblLang1().setEnabled(true);
				viewMF.getLblLang2().setEnabled(true);
				//viewMF.getTxtLang1().setEnabled(true);
				viewMF.getTxtLang2().setEnabled(true);
				viewMF.getBtnCheck().setEnabled(true);
				viewMF.getBtnNext().setEnabled(true);
				viewMF.getBtnView().setEnabled(true);
				viewMF.getLblProgress().setEnabled(true);
				viewMF.getProgressBar().setEnabled(true);
				
				viewMF.getTxtLang2().setText("");//Language 2 Textbox l�schen
				viewMF.deleteCorrectIcon();//CorrectIcon l�schen
				viewMF.getBtnLearn().setEnabled(false);//Lernen Button disablen
				System.out.println("ID " + viewMF.getComboBoxSelectWordpairSet().getSelectedIndex());
				learn(viewMF.getComboBoxSelectWordpairSet().getSelectedIndex()); //lernen starten mit Item aus Combobox
			}else {
				System.out.println("No WordpairSet in Combobox!");
			}
		}
	}
	
	//Button Save: Speichert den Stand als XML im Programmfolder.
		class btnSaveAListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				saveState();
			}
		}
	
	//Menu Import Importiert Sprachdatei
	class mntmImportWordlistAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			importWordList();			
		}
	}
	
	/*
	 * Start Menu Listener Klassen
	 */
	
	//Menu Export Exportiert Sprachdatei
	class mntmExportAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser fileChooser = new JFileChooser();
				
				// Filter setzten, damit nur XML Dateien gespreichert werden koennen
				fileChooser.setFileFilter(new LibJFileChooserFilter(".xml"));
				
				// "Alle Dateien" Filter deaktivieren
				fileChooser.setAcceptAllFileFilterUsed(false);
				
				// Dialog zum speichern von Dateien anzeigen. Als 1. Parameter wird das uebergeordnete Fenster erwartet,
				// damit dieses gesperrt werden kann
				int save = fileChooser.showDialog(null, ResourceBundle.getBundle("locale.messages", LibGlbSet.getLocale()).getString("exportWordlist"));
				
				// Pruefen ob auf oeffnen geklickt wurde
				if(save == JFileChooser.APPROVE_OPTION) {
					String filename = fileChooser.getSelectedFile().getAbsolutePath();
					if(!filename.endsWith(".xml")) {
						filename += ".xml";
					}
					
					// TODO filename in Settings speichern
					xmlFilename = filename;
					LibXMLWriter.exportModel(xmlFilename, model);
				}
			} catch (Exception ef) {
				ef.getStackTrace();
			}
		}
	}
	
	// Ruft EditorFenster auf
	class mntmNewWordlistAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ControllerWordlistEditor(getController());
		}
	}
	
	//Ruft About Fenster auf
	class mntmAboutAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ControllerAbout();// Controller f�r About wird erstellt
		}
	}
	
	
	//Ruft Edit Wordlist Fenster auf
	class mntmEditWordlistAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new ControllerWordlistEditor(getController());// Controller f�r WordlistEditor wird erstellt
		}
	}
	/*
	 * Start Restliche Listener Klassen
	 */
	
	//ComboBox �nderungen
	class comboBoxSelectLangItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				int index = viewMF.getComboBoxSelectLang().getSelectedIndex();
				if (index >= 0) { //Abfangen, da beim erstimport -1 zur�ckgegeben wird
					System.out.println("Sprache ge�ndert in index: " + index);
					setActiveLanguage(model.getLanguages().get(index));
					fillWordpairSetComboBox();
				}
            }  
			
		}
	}
	
	//ComboBox �nderungen
	class comboBoxSelectWordpairSetAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			viewMF.getBtnLearn().setEnabled(true);
		}
	}
	
	// Bei ENTER-Taste gleiche Aktion wie bei Check-Button
    class TxtLang2KeyListener implements KeyListener {
        public void keyPressed(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            	checkWordInput();
        }
		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}
    };
    
    public ViewMainFrame getView() {
    	return viewMF;
    }
    
    public Model getModel() {
    	return model;
    }
    
    public ModelLanguage getActiveLang() {
    	return activeLang;
    }
    
    public ModelWordpairSet getActiveWordpairSet() {
    	return activeWordpairSet;
    }
    
    public Controller getController() {
    	return this;
    }
    
    /**
	 * CombItem
	 * Vereinfacht das hinzuf�gen von Comboboxeintr�gen
	 */
	class ComboItem {
		private int key;
	    private String value;

	    public ComboItem(int key, String value)
	    {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public String toString()
	    {
	        return value;
	    }

	    public int getKey()
	    {
	        return key;
	    }

	    public String getValue()
	    {
	        return value;
	    }
	}
}
