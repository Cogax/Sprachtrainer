package WordTrainer;

import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import WordTrainer.ControllerWordlistEditor.WordpairTableModel;

/**
 * Das ist die GUI Klasse für den Wordlist Editor. Sie erstellt nur die Oberfläche und besitzt
 * weder Logik noch Daten der Models. 
 * 
 * Die einzelnen GUI Komponenten werder per get-Methoden dem Controller zur verfügung
 * gestellt.
 * 				
 * @version 1.0
 * @category View
 * 
 * Das Copyright liegt bei den Autoren:
 * @author Andreas Gyr
 * @author Daniel Ammann
 * @author Benjamin Lutz
 * @author Nico Florin
 * @author Habib Abdelbaki
 */
public class ViewWordlistEditor extends JFrame {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * GUI Komponenten
	 */
	private JPanel contentPane;
	private JTable tableWordList;
	private JTextField txtName;
	private JButton btnSave;
	private JButton btnExport;
	private JButton btnExit;
	private JComboBox cmbBoxLang1;
	private JComboBox cmbBoxLang2;
	private JSeparator verSep;
	private JSeparator horSep;
	private JTextField txtWord1;
	private JTextField txtWord2;
	private JButton btnSaveWord;
	private JSeparator horSep2;
	private JTextField txtSearch;
	private WordpairTableModel wptModel;
	private TableRowSorter<WordpairTableModel> tableSorter;
	private JButton btnDel;
	
	
	/**
	 * Kontruktor
	 * Setzt "Look and Feel" für das entsprechende OS
	 */
	public ViewWordlistEditor() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Force SystemLookAndFeel
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		initialize();
	}
	
	
	/**
	 * Initialisiert das gesammte GUI
	 */
	private void initialize() {
		// Frame
		this.setTitle(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.title")); //$NON-NLS-1$ //$NON-NLS-2$
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 528, 566);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// Panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Save Button
		btnSave = new JButton("Save"); // TODO language bundles
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBounds(378, 5, 130, 25);
		btnSave.setEnabled(false);
		contentPane.add(btnSave);
		
		// Export Button
		btnExport = new JButton("CSV Export"); // TODO language bundles
		btnExport.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExport.setBounds(378, 35, 130, 25);
		btnExport.setEnabled(false);
		contentPane.add(btnExport);
		
		// Exit Button
		btnExit = new JButton(ResourceBundle.getBundle("locale.messages").getString("ViewWordlistEditor.btnExit.text")); // TODO language bundles //$NON-NLS-1$ //$NON-NLS-2$
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExit.setBounds(378, 65, 130, 25);
		contentPane.add(btnExit);
		
		// Vertikal Seperator
		verSep = new JSeparator(JSeparator.VERTICAL);
		verSep.setBounds(368, 0, 2, 100);
		contentPane.add(verSep);
		
		// Name Label
		JLabel lblDescription = new JLabel(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.lblDescription")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDescription.setBounds(10, 6, 160, 22);
		contentPane.add(lblDescription);
		
		// Name Textfield
		txtName = new JTextField();
		txtName.setBounds(10, 28, 350, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		// Lang1 Label
		JLabel lblLanguage1 = new JLabel(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.lblLanguage1")); //$NON-NLS-1$ //$NON-NLS-2$
		lblLanguage1.setBounds(10, 50, 160, 24);
		contentPane.add(lblLanguage1);
		
		// Lang1 Combobox
		cmbBoxLang1 = new JComboBox();
		cmbBoxLang1.setBounds(10, 71, 160, 20);
		contentPane.add(cmbBoxLang1);
		
		// -> Label
		JLabel lblArrow = new JLabel("->");
		lblArrow.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblArrow.setBounds(178, 73, 46, 14);
		contentPane.add(lblArrow);
		
		// Lang2 Label
		JLabel lblLang2 = new JLabel(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.lblLanguage2")); //$NON-NLS-1$ //$NON-NLS-2$
		lblLang2.setBounds(198, 50, 160, 22);
		contentPane.add(lblLang2);
		
		// Lang2 Combobox
		cmbBoxLang2 = new JComboBox();
		cmbBoxLang2.setBounds(198, 71, 160, 20);
		cmbBoxLang2.setEnabled(false);
		contentPane.add(cmbBoxLang2);
		
		// Horizontal Seperator
		horSep = new JSeparator();
		horSep.setBounds(0, 100, 528, 425);
		contentPane.add(horSep);
		
		// Word1 Label
		// TODO dynamisch gewählte Sprache anzeigen
		JLabel lblWord1 = new JLabel(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.lblword1")); //$NON-NLS-1$ //$NON-NLS-2$
		lblWord1.setBounds(10, 105, 300, 22);
		contentPane.add(lblWord1);
		
		// Word1 Textfield
		txtWord1 = new JTextField();
		txtWord1.setBounds(10, 125, 240, 30);
		contentPane.add(txtWord1);
		txtWord1.setColumns(10);
		
		// -> Label
		JLabel lblArrow2 = new JLabel("->");
		lblArrow2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblArrow2.setBounds(253, 132, 20, 14);
		contentPane.add(lblArrow2);
		
		// Word2 Label
		// TODO dynamisch gewählte Sprache anzeigen
		JLabel lblWord2 = new JLabel(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.lblword2")); //$NON-NLS-1$ //$NON-NLS-2$
		lblWord2.setBounds(270, 105, 242, 22);
		contentPane.add(lblWord2);
		
		// Word2 Textfield
		txtWord2 = new JTextField();
		txtWord2.setBounds(270, 125, 240, 30);
		contentPane.add(txtWord2);
		
		// SaveWord Button
		btnSaveWord = new JButton("Save Word"); // TODO bundles
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSaveWord.setBounds(200, 165, 120, 30);
		contentPane.add(btnSaveWord);
		
		// HorSep2 Seperator
		horSep2 = new JSeparator();
		horSep2.setBounds(0, 203, this.getWidth(), this.getHeight());
		contentPane.add(horSep2);
		
		// Search Label
		JLabel lblSearch = new JLabel(ResourceBundle.getBundle("locale.messages").getString("ViewWordEditor.btnsearch")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSearch.setBounds(10, 210, 50, 22);
		contentPane.add(lblSearch);
		
		// Filter Textfield
		txtSearch = new JTextField();
		txtSearch.setBounds(60, 210, 150, 20);
		contentPane.add(txtSearch);
		
		// Löschen Button
		btnDel = new JButton("Delete"); // TODO bundles
		btnDel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDel.setBounds(405, 210, 100, 25);
		contentPane.add(btnDel);
		
		// Table
		wptModel = new ControllerWordlistEditor.WordpairTableModel();
		tableSorter = new TableRowSorter<WordpairTableModel>(wptModel);
		wptModel.addColumn("language 1");	// Nicht beachten!!
		wptModel.addColumn("language 2");	// Nicht beachten!!
		tableWordList = new JTable(wptModel);
		tableWordList.setRowSorter(tableSorter);
		tableWordList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane scrollPane = new JScrollPane(tableWordList);
		scrollPane.setBounds(10, 240, 500, 285);
		contentPane.add(scrollPane);
		
		// Validate
		this.validate();
	}

	
	/**
	 * Getter Methoden
	 * Geben die GUI Komponenten zurück
	 */
	public JPanel getContentPane() {
		return contentPane;
	}

	public JTable getTableWordList() {
		return tableWordList;
	}

	public JTextField getTxtName() {
		return txtName;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public JButton getBtnExport() {
		return btnExport;
	}

	public JButton getBtnExit() {
		return btnExit;
	}

	public JComboBox getCmbBoxLang1() {
		return cmbBoxLang1;
	}

	public JComboBox getCmbBoxLang2() {
		return cmbBoxLang2;
	}

	public JTextField getTxtWord1() {
		return txtWord1;
	}

	public JTextField getTxtWord2() {
		return txtWord2;
	}

	public JButton getBtnSaveWord() {
		return btnSaveWord;
	}
	
	public JTextField getTxtSearch() {
		return txtSearch;
	}
	
	public WordpairTableModel getWptModel() {
		return wptModel;
	}
	
	public TableRowSorter<WordpairTableModel> getTableSorter() {
		return tableSorter;
	}
	
	public JButton getBtnDel() {
		return btnDel;
	}
}
