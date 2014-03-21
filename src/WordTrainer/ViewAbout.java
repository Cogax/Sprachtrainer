package WordTrainer;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Das ist die GUI Klasse für das About-Fenster. Sie erstellt nur die Oberfläche und besitzt
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
public class ViewAbout extends JFrame {
	
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * GUI Komponenten
	 */
	private JFrame frmAbout;
	private JButton btnAppButtonClose;
	
	
	/**
	 * Kontruktor
	 * Setzt "Look and Feel" für das entsprechende OS
	 */
	public ViewAbout() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Force System-LookAndFeel
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
		frmAbout = new JFrame();
		frmAbout.setResizable(false);
		frmAbout.setTitle(ResourceBundle.getBundle("locale.messages", LibGlbSet.getLocale()).getString("about"));
		frmAbout.setBounds(100, 100, 332, 261);
		frmAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAbout.setLocationRelativeTo(null);
		
		// Main Panel
		JPanel panelMain = new JPanel();
		frmAbout.getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));
		
		// App Name Panel
		JPanel panelAppName = new JPanel();
		panelMain.add(panelAppName, BorderLayout.NORTH);
		
		// App Name Label
		JLabel lblAppName = new JLabel(ResourceBundle.getBundle("locale.messages", LibGlbSet.getLocale()).getString("title") + " v" + Controller.VERSION);
		panelAppName.add(lblAppName);
		
		// App Info Panel
		JPanel panelAppInfo = new JPanel();
		panelMain.add(panelAppInfo, BorderLayout.CENTER);
		panelAppInfo.setLayout(new BoxLayout(panelAppInfo, BoxLayout.Y_AXIS));
		
		// App Info Logo Panel
		JPanel panelAppInfoLogo = new JPanel();
		panelAppInfo.add(panelAppInfoLogo);
		
		// Copyright Label
		JLabel lblAppInfoCopyrightCBy = new JLabel("<html>Copyright \u00A9 2014 <br />Andreas Gyr <br /> Daniel Ammann <br /> Benjamin Lutz <br /> Nico Florin <br /> Habib Abdelbaki</html>");
		panelAppInfoLogo.add(lblAppInfoCopyrightCBy);
		
		// Copyright Panel
		JPanel panelAppInfoCopyright = new JPanel();
		panelAppInfoLogo.add(panelAppInfoCopyright);
		
		// Logo Label
		JLabel lblAppInfoLogologo = new JLabel(new ImageIcon(ViewAbout.class.getResource("/images/globus.png")));
		panelAppInfoLogo.add(lblAppInfoLogologo);
		
		// Button Panel
		JPanel panelAppButton = new JPanel();
		panelMain.add(panelAppButton, BorderLayout.SOUTH);
		
		// Close Button
		btnAppButtonClose = new JButton("Close");
		panelAppButton.add(btnAppButtonClose);

	}

	/**
	 * Bestimmt die sichtbarkeit des Fensters
	 * 
	 * @param boolen 
	 */
	public void setVisible(boolean b) {
		this.frmAbout.setVisible(b);
	}

	/**
	 * Getter Methoden
	 * Geben die GUI Komponenten zurück
	 */
	public JButton getbtnAppButtonClose() {
		return btnAppButtonClose;
	}
	
	public JFrame getFrmAbout() {
		return frmAbout;
	}
}