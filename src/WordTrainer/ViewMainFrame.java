package WordTrainer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 * Das ist die GUI Klasse für das Hauptfenster. Sie erstellt nur die Oberfläche und besitzt
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
public class ViewMainFrame extends JFrame {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * GUI Komponenten
	 */
	private JPanel contentPane;
	private JTextField txtLang1;
	private JTextField txtLang2;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmImportWordlist;
	private JMenuItem mntmNewWordlist;
	private JMenuItem mntmExit;
	private JMenu mnEdit;
	private JMenuItem mntmEditWordlist;
	private JMenu mnSettings;
	private JMenu mnChangeLanguage;
	private JMenuItem mntmGerman;
	private JMenuItem mntmEnglish;
	private JMenuItem mntmFrench;
	private JMenuItem mntmSpanish;
	private JMenuItem mntmItalian;
	private JMenu mnHelp;
	private JMenuItem mntmAbout;
	private JProgressBar progressBar;
	private JLabel lblProgress;
	private JCheckBox chckbxSetAsDefault;
	private JButton btnCheck;
	private JButton btnNext;
	private JButton btnView;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxSelectLang;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxSelectWordpairSet;
	private JLabel lblLang1;
	private JLabel lblLang2;
	private JLabel lblSelectLang;
	private JButton btnImportWordlist;
	private JButton btnSave;
	private JButton btnLearn;
	private JLabel lblIconCorrect;

	
	/**
	 * Kontruktor
	 * Setzt "Look and Feel" für das entsprechende OS
	 */
	public ViewMainFrame() {
		//Globale Locale setzen
		Locale.setDefault(Locale.ENGLISH);
		
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
	@SuppressWarnings("rawtypes")
	private void initialize() {
		// Frame
		this.setTitle(ResourceBundle.getBundle("locale.messages").getString("title") + " v" + Controller.VERSION); //$NON-NLS-1$ //$NON-NLS-2$
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 472, 502);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// Menubar
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Menu: File
		mnFile = new JMenu(ResourceBundle.getBundle("locale.messages").getString("file")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(mnFile);
		
		// MenuItem: Save
		mntmSave = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("save")); //$NON-NLS-1$ //$NON-NLS-2$
		mnFile.add(mntmSave);

		// MenuItem: Save As
		mntmSaveAs = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("saveAs")); //$NON-NLS-1$ //$NON-NLS-2$
		mnFile.add(mntmSaveAs);
		
		// MenuItem: Import Wordlist
		mntmImportWordlist = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("importWordlist")); //$NON-NLS-1$ //$NON-NLS-2$
		mnFile.add(mntmImportWordlist);
		
		// MenuItem: New Wordlist
		mntmNewWordlist = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("newWordlist"));
		mnFile.add(mntmNewWordlist);

		// MenuItem: Exit
		mntmExit = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("exit")); //$NON-NLS-1$ //$NON-NLS-2$
		mnFile.add(mntmExit);

		// Exit Listener
		// TODO save?? --> Controller
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Programm schließen
				System.exit(0);
			}
		});

		// Menu: Edit
		mnEdit = new JMenu(ResourceBundle.getBundle("locale.messages").getString("edit")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(mnEdit);
		
		// MenuItem: Edit Wordlist
		mntmEditWordlist = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("ViewMainFrame.mntmNewMenuItem")); //$NON-NLS-1$ //$NON-NLS-2$
		mnEdit.add(mntmEditWordlist);

		// Menu: Settings
		mnSettings = new JMenu(ResourceBundle.getBundle("locale.messages").getString("settings")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(mnSettings);
		
		// Menu: Language
		mnChangeLanguage = new JMenu(ResourceBundle.getBundle("locale.messages").getString("changeLanguage")); //$NON-NLS-1$ //$NON-NLS-2$
		mnSettings.add(mnChangeLanguage);
		
		// MenuItem: German
		mntmGerman = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("german")); //$NON-NLS-1$ //$NON-NLS-2$
		mnChangeLanguage.add(mntmGerman);
		mntmGerman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Locale.GERMAN);
			}
		});

		// MenuItem: Englisch
		mntmEnglish = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("english")); //$NON-NLS-1$ //$NON-NLS-2$
		mnChangeLanguage.add(mntmEnglish);
		mntmEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Locale.ENGLISH);
			}
		});

		// MenuItem: French
		mntmFrench = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("french")); //$NON-NLS-1$ //$NON-NLS-2$
		mnChangeLanguage.add(mntmFrench);
		mntmFrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Locale.FRENCH);
			}
		});

		// MenuItem: Spanish
		mntmSpanish = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("spanish")); //$NON-NLS-1$ //$NON-NLS-2$
		mnChangeLanguage.add(mntmSpanish);
		mntmSpanish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Locale.forLanguageTag("es"));
			}
		});

		// MenuItem: Italian
		mntmItalian = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("italian")); //$NON-NLS-1$ //$NON-NLS-2$
		mnChangeLanguage.add(mntmItalian);
		mntmItalian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeLanguage(Locale.ITALIAN);
			}
		});

		// Menu: Help
		mnHelp = new JMenu(ResourceBundle.getBundle("locale.messages").getString("help")); //$NON-NLS-1$ //$NON-NLS-2$
		menuBar.add(mnHelp);
		
		// MenuItem: About
		mntmAbout = new JMenuItem(ResourceBundle.getBundle("locale.messages").getString("about")); //$NON-NLS-1$ //$NON-NLS-2$
		mnHelp.add(mntmAbout);

		// Panel
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Sprache 1 Textfeld
		txtLang1 = new JTextField();
		txtLang1.setEnabled(false);
		txtLang1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLang1.setBounds(10, 177, 222, 46);
		contentPane.add(txtLang1);
		txtLang1.setColumns(10);

		// Sprache 2 Textfeld
		txtLang2 = new JTextField();
		txtLang2.setEnabled(false);
		txtLang2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLang2.setBounds(10, 247, 222, 46);
		contentPane.add(txtLang2);
		txtLang2.setColumns(10);

		// Sprachauswahl Combobox
		comboBoxSelectLang = new JComboBox();
		comboBoxSelectLang.setEnabled(false);
		comboBoxSelectLang.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxSelectLang.setBounds(150, 32, 304, 31);
		contentPane.add(comboBoxSelectLang);
		
		// Wordlistauswahl Combobox
		comboBoxSelectWordpairSet = new JComboBox();
		comboBoxSelectWordpairSet.setEnabled(false);
		comboBoxSelectWordpairSet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxSelectWordpairSet.setBounds(150, 87, 304, 31);
		contentPane.add(comboBoxSelectWordpairSet);

		// Progressbar
		progressBar = new JProgressBar();
		progressBar.setEnabled(false);
		progressBar.setBounds(10, 413, 446, 28);
		contentPane.add(progressBar);

		// Progress Label
		lblProgress = new JLabel(ResourceBundle.getBundle("locale.messages").getString("progress")); //$NON-NLS-1$ //$NON-NLS-2$
		lblProgress.setEnabled(false);
		lblProgress.setBounds(10, 388, 78, 14);
		contentPane.add(lblProgress);

		// Set as default Checkbox
		chckbxSetAsDefault = new JCheckBox(ResourceBundle.getBundle("locale.messages").getString("setDefaultLang")); //$NON-NLS-1$ //$NON-NLS-2$
		chckbxSetAsDefault.setEnabled(false);
		chckbxSetAsDefault.setBounds(150, 125, 183, 23);
		contentPane.add(chckbxSetAsDefault);

		// Check Button
		btnCheck = new JButton(ResourceBundle.getBundle("locale.messages").getString("check")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCheck.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCheck.setEnabled(false);
		btnCheck.setBounds(10, 316, 130, 50);
		contentPane.add(btnCheck);

		// Next Button
		btnNext = new JButton(ResourceBundle.getBundle("locale.messages").getString("next")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNext.setEnabled(false);
		btnNext.setBounds(324, 316, 130, 50);
		contentPane.add(btnNext);

		// View Button
		btnView = new JButton(ResourceBundle.getBundle("locale.messages").getString("view")); //$NON-NLS-1$ //$NON-NLS-2$
		btnView.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnView.setEnabled(false);
		btnView.setBounds(166, 316, 130, 50);
		contentPane.add(btnView);
		
		// Import Wordlist Button
		btnImportWordlist = new JButton(ResourceBundle.getBundle("locale.messages").getString("importWordlist")); //$NON-NLS-1$ //$NON-NLS-2$
		btnImportWordlist.setBounds(10, 27, 130, 44);
		contentPane.add(btnImportWordlist);
		
		// Lern Button
		btnLearn = new JButton(ResourceBundle.getBundle("locale.messages").getString("learn")); //$NON-NLS-1$ //$NON-NLS-2$
		btnLearn.setEnabled(false);
		btnLearn.setBounds(349, 129, 105, 34);
		contentPane.add(btnLearn);
		
		// Select Lang Label
		lblSelectLang = new JLabel(ResourceBundle.getBundle("locale.messages").getString("selLangToLearn")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSelectLang.setBounds(150, 11, 304, 14);
		contentPane.add(lblSelectLang);
		
		// Sprache 1 Label
		lblLang1 = new JLabel("");
		lblLang1.setEnabled(false);
		lblLang1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLang1.setBounds(242, 177, 142, 46);
		contentPane.add(lblLang1);
		
		// Sprache 2 Label
		lblLang2 = new JLabel("");
		lblLang2.setEnabled(false);
		lblLang2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLang2.setBounds(242, 247, 142, 46);
		contentPane.add(lblLang2);
		
		// Icon Label
		lblIconCorrect = new JLabel("");
		lblIconCorrect.setBounds(394, 208, 60, 60);
		contentPane.add(lblIconCorrect);
		
		// Save Button
		btnSave = new JButton(ResourceBundle.getBundle("locale.messages").getString("save")); //$NON-NLS-1$ //$NON-NLS-2$
		btnSave.setBounds(10, 81, 130, 46);
		contentPane.add(btnSave);
	
		// Validate
		this.validate();
	}
	
	/**
	 * Setzt das korrekt oder falsch Icon für eine kurze Zeit
	 */
	public void setCorrectIcon(boolean correct) {  
		if (correct) {
	        this.lblIconCorrect.setIcon(new ImageIcon(ViewMainFrame.class.getResource("/images/right.png")));
			// TODO Timer das Korrekt kurz angezeigt wird
		}
		else 
			this.lblIconCorrect.setIcon(new ImageIcon(ViewMainFrame.class.getResource("/images/wrong.png")));
	}

	/**
	 * Wechselt die Sprache der GUI und initialisiert das GUI neu
	 * @param locale die Sprache
	 */
	private void changeLanguage(Locale l) {
		if (!l.equals(Locale.getDefault())) { //Damit GUI nicht geändert wird wenn dieselbe Sprache nochmals ausgewählt wird
			Locale.setDefault(l);
			
			this.setTitle(ResourceBundle.getBundle("locale.messages").getString("title") + " v" + Controller.VERSION); //$NON-NLS-1$ //$NON-NLS-2$
			mnFile.setText(ResourceBundle.getBundle("locale.messages").getString("file")); //$NON-NLS-1$ //$NON-NLS-2$
			mnFile.setText(ResourceBundle.getBundle("locale.messages").getString("file")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmSave.setText(ResourceBundle.getBundle("locale.messages").getString("save")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmSaveAs.setText(ResourceBundle.getBundle("locale.messages").getString("saveAs")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmImportWordlist.setText(ResourceBundle.getBundle("locale.messages").getString("importWordlist")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmExit.setText(ResourceBundle.getBundle("locale.messages").getString("exit")); //$NON-NLS-1$ //$NON-NLS-2$
			mnEdit.setText(ResourceBundle.getBundle("locale.messages").getString("edit")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmEditWordlist.setText(ResourceBundle.getBundle("locale.messages").getString("ViewMainFrame.mntmNewMenuItem")); //$NON-NLS-1$ //$NON-NLS-2$
			mnSettings.setText(ResourceBundle.getBundle("locale.messages").getString("settings")); //$NON-NLS-1$ //$NON-NLS-2$
			mnChangeLanguage.setText(ResourceBundle.getBundle("locale.messages").getString("changeLanguage")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmGerman.setText(ResourceBundle.getBundle("locale.messages").getString("german")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmEnglish.setText(ResourceBundle.getBundle("locale.messages").getString("english")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmFrench.setText(ResourceBundle.getBundle("locale.messages").getString("french")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmSpanish.setText(ResourceBundle.getBundle("locale.messages").getString("spanish")); //$NON-NLS-1$ //$NON-NLS-2$
			mntmItalian.setText(ResourceBundle.getBundle("locale.messages").getString("italian")); //$NON-NLS-1$ //$NON-NLS-2$
			mnHelp.setText(ResourceBundle.getBundle("locale.messages").getString("help")); //$NON-NLS-1$ //$NON-NLS-2$
			lblProgress.setText(ResourceBundle.getBundle("locale.messages").getString("progress")); //$NON-NLS-1$ //$NON-NLS-2$
			chckbxSetAsDefault.setText(ResourceBundle.getBundle("locale.messages").getString("setDefaultLang")); //$NON-NLS-1$ //$NON-NLS-2$
			btnCheck.setText(ResourceBundle.getBundle("locale.messages").getString("check")); //$NON-NLS-1$ //$NON-NLS-2$
			btnNext.setText(ResourceBundle.getBundle("locale.messages").getString("next")); //$NON-NLS-1$ //$NON-NLS-2$
			btnView.setText(ResourceBundle.getBundle("locale.messages").getString("view")); //$NON-NLS-1$ //$NON-NLS-2$
			btnImportWordlist.setText(ResourceBundle.getBundle("locale.messages").getString("importWordlist")); //$NON-NLS-1$ //$NON-NLS-2$
			btnLearn.setText(ResourceBundle.getBundle("locale.messages").getString("learn")); //$NON-NLS-1$ //$NON-NLS-2$
			lblSelectLang.setText(ResourceBundle.getBundle("locale.messages").getString("selLangToLearn")); //$NON-NLS-1$ //$NON-NLS-2$
			btnSave.setText(ResourceBundle.getBundle("locale.messages").getString("save")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}
	
	
	/**
	 * Löscht das Correct / False Icon
	 */
	public void deleteCorrectIcon() {
		this.lblIconCorrect.setIcon(null);
	}

	
	/**
	 * Getter Methoden
	 * Geben die GUI Komponenten zurück
	 */
	public JTextField getTxtLang1() {
		return txtLang1;
	}

	public JTextField getTxtLang2() {
		return txtLang2;
	}

	public JMenu getMnFile() {
		return mnFile;
	}
	
	public JMenuItem getMntmSave() {
		return mntmSave;
	}

	public JMenuItem getMntmImportWordlist() {
		return mntmImportWordlist;
	}

	public JMenuItem getMntmSaveAs() {
		return mntmSaveAs;
	}

	public JMenu getMnEdit() {
		return mnEdit;
	}

	public JMenuItem getMntmEditWordlist() {
		return mntmEditWordlist;
	}

	public JMenuItem getMntmGerman() {
		return mntmGerman;
	}

	public JMenuItem getMntmEnglish() {
		return mntmEnglish;
	}

	public JMenuItem getMntmFrench() {
		return mntmFrench;
	}

	public JMenuItem getMntmSpanish() {
		return mntmSpanish;
	}

	public JMenuItem getMntmItalian() {
		return mntmItalian;
	}

	public JMenu getMnHelp() {
		return mnHelp;
	}

	public JMenuItem getMntmAbout() {
		return mntmAbout;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public JLabel getLblProgress() {
		return lblProgress;
	}

	public JCheckBox getChckbxSetAsDefault() {
		return chckbxSetAsDefault;
	}

	public JButton getBtnCheck() {
		return btnCheck;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public JButton getBtnView() {
		return btnView;
	}

	public JLabel getLblLang1() {
		return lblLang1;
	}

	public JLabel getLblLang2() {
		return lblLang2;
	}

	public JButton getBtnImportWordlist() {
		return btnImportWordlist;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public JButton getBtnLearn() {
		return btnLearn;
	}
	
	@SuppressWarnings("rawtypes")
	public JComboBox getComboBoxSelectLang() {
		return comboBoxSelectLang;
	}
	
	@SuppressWarnings("rawtypes")
	public JComboBox getComboBoxSelectWordpairSet() {
		return comboBoxSelectWordpairSet;
	}

	public JLabel getLblIconCorrect() {
		return lblIconCorrect;
	}
	
	public JMenuItem getMntmNewWordlist() {
		return mntmNewWordlist;
	}
}
