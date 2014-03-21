package WordTrainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class ControllerWordlistEditor {
	private ViewWordlistEditor 	viewWLE;				// View Objekt
	private Controller			controller;				// Controller Objekt
	private Model				model;					// Model Objekt (wird vom MainController übergeben)
	private ModelLanguage 		activeLang;				// Gewählte Sprache (wird vom MainController übergeben)
	private ModelWordpairSet 	activeWordpairSet;		// Gewählte Wordlist (wird vom MainController übergeben)
	private static String 		lang1;					//  
	private static String 		lang2;					//

	/*
	 * WordEditor Controller
	 */
	public ControllerWordlistEditor(Controller controller) {
		this.controller = controller;
		this.model = controller.getModel();
		activeLang = controller.getActiveLang();
		activeWordpairSet = controller.getActiveWordpairSet();
		init();
	}

	private void init() {
		// Felder definieren
		this.viewWLE = new ViewWordlistEditor();
		
		// Window Listener
		viewWLE.addWindowListener(new viewWListener());
		
		// Button Listener registrieren
		viewWLE.getBtnSaveWord().addActionListener(new btnSaveWordAListener());
		viewWLE.getBtnDel().addActionListener(new btnDelAListener());
		viewWLE.getBtnExport().addActionListener(new btnExportAListener());
		viewWLE.getBtnExit().addActionListener(new btnExitAListener());
		
		// Combobox Listener registrieren
		viewWLE.getCmbBoxLang1().addActionListener(new comboBoxSelectLang1AListener());
		viewWLE.getCmbBoxLang2().addActionListener(new comboBoxSelectLang2AListener());
		
		// Txt Feld Listener
		viewWLE.getTxtWord1().addKeyListener(new txtWord1KListener());
		viewWLE.getTxtWord2().addKeyListener(new txtWord2KListener());
		viewWLE.getTxtSearch().getDocument().addDocumentListener(new tableWordListDListener());
		viewWLE.getTxtName().addKeyListener(new txtNameKListener());
		
		// Table Listeners

		// ComboBox füllen
		fillLang1ComboBox();
		
		// Falls eine Wordlist bearbeitet wird, werden deren Daten geladen
		loadExistingWordlistData();
		
		// Prüft diverse GUI Elemente ab, ob sie Enabled oder Disabled gesetzt werden sollen
		checkEnableExportBtn();
		checkEnableAddWordRegion();
		checkEnableSaveBtn();
		
		// set viewWordlistEditor visible
		viewWLE.setVisible(true);
	}
	
	/**
	 * Prüft ob eine Wordlist bearbeitet wird, falls ja werden die Daten der Wordlist
	 * in die Felder Name, sowie die Lang CmbBoxen eingefügt
	 */
	private void loadExistingWordlistData() {
		System.out.println("Eine Wortliste soll bearbeitet werden");
		
		// WordpairSet
		if(activeWordpairSet != null) {
			// Name
			viewWLE.getTxtName().setText(activeWordpairSet.getName());
			viewWLE.getTxtName().setEnabled(false);
			viewWLE.getWptModel().removeAllRows();
			System.out.println("** Count after Remove All: " + viewWLE.getWptModel().getRowCount());
			
			// Wordpairs in Tabelle
			for(ModelWordpair wp: activeWordpairSet.getWordpairs()) {
				viewWLE.getWptModel().addWordpair(wp);
			}
		}
		
		// Language
		if(activeLang != null) {
			System.out.println("activeLang != null");
			
			// Lang 1 Combobox mit der Sprache füllen und disablen
			JComboBox boxLang1 = viewWLE.getCmbBoxLang1();
			boxLang1.removeAllItems();
			System.out.println("Selectlang1 Count: " + boxLang1.getItemCount());
			boxLang1.addItem(new ComboItem(activeLang.getLang1(), ResourceBundle.getBundle("locale.messages").getString(activeLang.getLang1())));
			boxLang1.setEnabled(false);
			System.out.println("Selectlang1 Count: " + boxLang1.getItemCount());
			
			// Lang 2 Combobox mit der Sprache füllen und disablen
			JComboBox boxLang2 = viewWLE.getCmbBoxLang2();
			boxLang2.removeAllItems();
			boxLang2.addItem(new ComboItem(activeLang.getLang2(), ResourceBundle.getBundle("locale.messages").getString(activeLang.getLang2())));
			boxLang2.setEnabled(false);
			System.out.println("Selectlang2 Count: " + boxLang1.getItemCount());
			
			// TODO rausfinden für was das ist - entfernen?
			lang1 = activeLang.getLang1();
			lang2 = activeLang.getLang2();
		} else {
			System.out.println("activeLang == null");
		}
	}
	
	/**
	 * Füllt die Lang1ComboBox
	 */
	private void fillLang1ComboBox() {
		JComboBox box = viewWLE.getCmbBoxLang1();
		box.removeAllItems();
		
		// Combobox füllen
		for(String l : LibGlbSet.langArr) {
			box.addItem(new ComboItem(l, ResourceBundle.getBundle("locale.messages").getString(l)));
		}
		
		if(box.getItemCount() > 0) {
			box.setEnabled(true);
		}
	}
	
	/**
	 * Füllt die Lang2ComboBox
	 */
	private void fillLang2ComboBox() {
		JComboBox box = viewWLE.getCmbBoxLang2();
		box.removeAllItems();
		
		for(String l : LibGlbSet.langArr) {
			if(!l.equals(lang1))
				box.addItem(new ComboItem(l, ResourceBundle.getBundle("locale.messages").getString(l)));
		}
		
		if(box.getItemCount() > 0) {
			box.setEnabled(true);
		}
	}
	
	/**
	 * Filtert die Tabelle
	 */
	private void newTableWordListFilter() {
		 RowFilter<WordpairTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
        	rf = RowFilter.regexFilter(viewWLE.getTxtSearch().getText(), 0);	// Filter für Column 0
            rf = RowFilter.regexFilter(viewWLE.getTxtSearch().getText(), 1);	// Filter für Column 1
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        viewWLE.getTableSorter().setRowFilter(rf);
	}
	
	/**
	 * Exportiert die Wortliste
	 */
	private void exportWordlist() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new LibJFileChooserFilter(".csv"));
			fileChooser.setAcceptAllFileFilterUsed(false);
			int save = fileChooser.showDialog(null, ResourceBundle.getBundle("locale.messages", LibGlbSet.getLocale()).getString("exportWordlist"));
			
			// Pruefen ob auf save geklickt wurde
			if(save == JFileChooser.APPROVE_OPTION) {
				LibCcvWriter writer = new LibCcvWriter(fileChooser.getSelectedFile().getAbsolutePath(), ';');
				
				// Erste Linie mit Lang Infos füllen
				String[] lineLang = new String[2];
				lineLang[0] = lang1;
				lineLang[1] = lang2;
				writer.addLine(lineLang);
				
				// Zweite Linie mit Namen füllen
				String[] lineName = new String[2];
				lineName[0] = "name";
				lineName[1] = viewWLE.getTxtName().getText();
				writer.addLine(lineName);
				
				// Restliche Linien mit Wörtern füllen
				for(Object w  : viewWLE.getWptModel().getRows()) {
					ModelWordpair wp = (ModelWordpair) w;
					String[] line = new String[2];
					line[0] = wp.getWord1();
					line[1] = wp.getWord2();
					writer.addLine(line);
				}
				
				writer.write();
			}
		} catch (Exception ef) {
			ef.getStackTrace();
		}
	}
	
	/**
	 * Prüft ob der ExportButton Enabled oder Disabled gemacht werden soll, und macht dies auch gleich
	 * Enable:	- Mehr als 0 Einträge in Tabelle && Name ausgefüllt
	 */
	private void checkEnableExportBtn() {
		if(viewWLE.getWptModel().getRowCount() > 0 && !viewWLE.getTxtName().getText().isEmpty()) {
			viewWLE.getBtnExport().setEnabled(true);
		} else {
			viewWLE.getBtnExport().setEnabled(false);
		}
	}
	
	/**
	 * Prüft ob die Textfelder Word1, Word2, SaveWordButton, SearchWordButton, DeleteWordButton 
	 * Enabled oder Disabled gemacht werden sollen und macht dies auch gleich.
	 * Enable:	- Textfeld Name ausgefüllt
	 */
	private void checkEnableAddWordRegion() {
		boolean enable;
		if(viewWLE.getTxtName().getText().isEmpty()) {
			enable = false;
		} else {
			enable = true;
		}
		
		viewWLE.getTxtWord1().setEnabled(enable);
		viewWLE.getTxtWord2().setEnabled(enable);
		viewWLE.getBtnSaveWord().setEnabled(enable);
		viewWLE.getBtnDel().setEnabled(enable);
		viewWLE.getTxtSearch().setEnabled(enable);
	}
	
	/**
	 * Prüft ob der Save Button Enabled oder Disabled gemacht werden soll,
	 * und macht dies auch gleich.
	 */
	private void checkEnableSaveBtn() {
		if(viewWLE.getWptModel().getRowCount() > 0) {
			viewWLE.getBtnSave().setEnabled(true);
		} else {
			viewWLE.getBtnSave().setEnabled(false);
		}
	}
	
	/**
	 * Speichert die Wortliste
	 */
	private void saveWordlist() {
		if(activeWordpairSet != null) {
			// Eine Liste wurde bearbeitet.
			activeWordpairSet.removeAllWordpairs();
			System.out.println("** Count WP of active WPS after remove all:" + activeWordpairSet.getWordpairs().size());
			for(Object w : viewWLE.getWptModel().getRows()) {
				activeWordpairSet.addWordpair((ModelWordpair)w);
			}
			System.out.println("** Count WP of active WPS after adding table content:" + activeWordpairSet.getWordpairs().size());
		} else {
			// prüfen ob ModelLanguage zu den gewählten Sprachen bereits im Model existiert,
			// ansonsten erstellen und dem Model zuordnen
			ModelLanguage modelLanguage = null;
			if(model.getLanguages().size() > 0) {
				for(ModelLanguage l : model.getLanguages()) {
					if(l.getLang1().equalsIgnoreCase(lang1) && l.getLang2().equalsIgnoreCase(lang2)) {
						modelLanguage = l;
					}
				}
			}
			if(modelLanguage == null) {
				modelLanguage = new ModelLanguage(lang1, lang2);
				model.addLanguage(modelLanguage);
			}
			
			// ModelWordpairSet erstellen und mit Wordpairs der Tabelle füllen, anschliessend dem
			// ModelLanguage Objekt des Models zuordnen
			// TODO prüfen ob Wordpairset mit diesem namen in der gewählten Lang schon existiert
			ModelWordpairSet modelWordpairSet = new ModelWordpairSet(viewWLE.getTxtName().getText());
			for(Object w : viewWLE.getWptModel().getRows()) {
				modelWordpairSet.addWordpair((ModelWordpair)w);
			}
			modelLanguage.addWordpairSet(modelWordpairSet);
			
			// Die eben erzeugten Objekte als aktiv setzen
			activeLang = modelLanguage;
			activeWordpairSet = modelWordpairSet;
			System.out.println(activeWordpairSet.getWordpairs().size());
			
			// CmbBoxen neu zeichen
			controller.fillLangComboBox();
			controller.fillWordpairSetComboBox();
			
			controller.getView().repaint();
		}
	}
	
	/**
	 * viewWLE WindowListener
	 */
	class viewWListener implements WindowListener {

		public void windowClosed(WindowEvent e) {
			saveWordlist();
		}
		
		public void windowActivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
	
	/**
	 * Save Button ActionListener
	 */
	class btnSaveAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			saveWordlist();
		}
	}
	
	/**
	 * Exit Button ActionListener
	 */
	class btnExitAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO "Sind Sie sicher, dass Sie nicht speicher wollen?"
			viewWLE.dispose();
		}
	}
	
	/**
	 * Export Button ActionListener
	 */
	class btnExportAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			exportWordlist();
		}
	}
	

	/**
	 * Delete Button ActionListener
	 */
	class btnDelAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int r : viewWLE.getTableWordList().getSelectedRows()) {
				viewWLE.getWptModel().removeRow(r);
				viewWLE.getTableWordList().repaint();
				System.out.println(viewWLE.getWptModel().getRowCount());
				
				// Wenn Tabelle 0 Einträge hat die Lang CmbBoxen enablen
				// Wenn Tabelle mehr als 0 Einträge hat Export Button enablen
				if(viewWLE.getWptModel().getRowCount() == 0) {
					viewWLE.getCmbBoxLang1().setEnabled(true);
					viewWLE.getCmbBoxLang2().setEnabled(true);
				}
				checkEnableExportBtn();
				checkEnableSaveBtn();
			}
		}	
	}
	
	/**
	 * tableWordList Tabelle DocumentListener (Filter)
	 */
	class tableWordListDListener implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			newTableWordListFilter();
		}

		public void insertUpdate(DocumentEvent e) {
			newTableWordListFilter();
		}

		public void removeUpdate(DocumentEvent e) {
			newTableWordListFilter();
		}
	}
	
	class txtNameKListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			checkEnableAddWordRegion();
		}

		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	
	/**
	 * Word 2 Texfeld KeyListener
	 */
	class txtWord2KListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				viewWLE.getBtnSaveWord().doClick();
			}
		}
		
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	
	/**
	 * Word 1 Texfeld KeyListener
	 */
	class txtWord1KListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				viewWLE.getTxtWord2().requestFocus();
			}
		}
		
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
	
	/**
	 * SaveWord Button ActionListener
	 */
	class btnSaveWordAListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String word1 = viewWLE.getTxtWord1().getText();
			String word2 = viewWLE.getTxtWord2().getText();
			
			// Falls kein Textfeld leer ist in die Tabelle einfügen
			if(!word1.isEmpty() && !word2.isEmpty()) {
				viewWLE.getWptModel().addWordpair(new ModelWordpair(word1, word2));
				viewWLE.getTxtWord1().setText("");		// Textfeld leeren
				viewWLE.getTxtWord2().setText("");		// Textfeld leeren
				viewWLE.getTxtWord1().requestFocus();	// Fokus setzten
			}
			
			// Falls Tabelle mehr als 0 EInträge hat die Comboboxen disablen / Export Button enablen
			if(viewWLE.getWptModel().getRowCount() > 0) {
				viewWLE.getCmbBoxLang1().setEnabled(false);
				viewWLE.getCmbBoxLang2().setEnabled(false);
			}
			checkEnableExportBtn();
			checkEnableSaveBtn();
		}
	}
	
	/**
	 * Lang1 Combobox ActionListener
	 */
	class comboBoxSelectLang1AListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Bei reamoveAllItem() wird auch dieser Listener angesprochen, der selectedIndex ist dann -1 -> NullPointerException
			if(viewWLE.getCmbBoxLang1().getSelectedIndex() >= 0) {
				ComboItem item = (ComboItem) viewWLE.getCmbBoxLang1().getSelectedItem();
				System.out.println("SelectLang1AListener selectedIndex:" + viewWLE.getCmbBoxLang1().getSelectedIndex());
				lang1 = item.getKey();
				
				// Column 0 ändern
				viewWLE.getTableWordList().getColumnModel().getColumn(0).setHeaderValue(ResourceBundle.getBundle("locale.messages").getString(lang1));
				viewWLE.getTableWordList().getTableHeader().repaint();
				
				fillLang2ComboBox();
			}
		}
	}
	
	/**
	 * Lang 2 Combobox ActionListener
	 */
	class comboBoxSelectLang2AListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ComboItem item = (ComboItem) viewWLE.getCmbBoxLang2().getSelectedItem();
			if(item != null) {
				lang2 = item.getKey();
				
				// Column 1 ändern
				viewWLE.getTableWordList().getColumnModel().getColumn(1).setHeaderValue(ResourceBundle.getBundle("locale.messages").getString(lang2));
				viewWLE.getTableWordList().getTableHeader().repaint();
				
				System.out.println("Sprachen gewählt: " + lang1 + " : " + lang2);
			}
		}
	}
	
	
	/**
	 * CombItem
	 * Vereinfacht das hinzufügen von Comboboxeinträgen
	 */
	class ComboItem {
		private String key;
	    private String value;

	    public ComboItem(String key, String value)
	    {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public String toString()
	    {
	        return value;
	    }

	    public String getKey()
	    {
	        return key;
	    }

	    public String getValue()
	    {
	        return value;
	    }
	}
	
	/**
	 * WordList Table Model
	 */
	static class WordpairTableModel implements TableModel {
		private Vector wordpairs = new Vector();
		private Vector listeners = new Vector();
		private Vector columns = new Vector();
		
		public void addWordpair(ModelWordpair wordpair) {
			int index = wordpairs.size();
			wordpairs.add(wordpair);
			
			TableModelEvent e = new TableModelEvent(this, index, index, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
			for( int i = 0, n = listeners.size(); i<n; i++ ){
				((TableModelListener)listeners.get( i )).tableChanged( e );
			}
			
		}
		
		public void addColumn(String c) {
			columns.add(c);
		}
		
		public int getColumnCount() {
			return 2;
		}
		
		public int getRowCount() {
			return wordpairs.size();
		}
		
		public Vector getRows() {
			return wordpairs;
		}
		
		public String getColumnName(int columnIndex) {
			return (String) columns.get(columnIndex);
		}
		
		public String getValueAt(int rowIndex, int columnIndex) {
			ModelWordpair w = (ModelWordpair)wordpairs.get(rowIndex);
			if(columnIndex == 0) {
				return w.getWord1();
			} else {
				return w.getWord2();
			}
		}
		
		public void removeRow(int r) {
			if(r != -1 && r < wordpairs.size()) {
				wordpairs.remove(r);
			}
		}
		
		public void removeAllRows() {
			wordpairs.removeAllElements();
		}
		
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		public void addTableModelListener(TableModelListener l) {
			listeners.add(l);
		}
		
		public void removeTableModelListener(TableModelListener l) {
			listeners.remove(l);
		}
		
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			ModelWordpair wp = (ModelWordpair) wordpairs.get(rowIndex);
			if(columnIndex == 0) {
				wp.setWord1((String)aValue);
			} else {
				wp.setWord2((String)aValue);
			}
		}
		
	}
}
