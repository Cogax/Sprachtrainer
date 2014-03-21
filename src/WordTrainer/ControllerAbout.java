package WordTrainer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MVC - Controller About: Controller f�r die ViewAbout
 * 
 * @author Andreas Gyr
 */

public class ControllerAbout {
	
	private ViewAbout viewAbout;

	/*
	 * About Controller - About
	 */
	public ControllerAbout( ) {
		init();
	}

	private void init() {
		// Felder definieren
		this.viewAbout = new ViewAbout();
		
		// Listener registrieren		
		viewAbout.getbtnAppButtonClose().addActionListener(new btnAppButtonCloseAListener());
		viewAbout.setVisible(true);
	}

	/*
	 * Start - listener Subklasse
	 */
	
	class btnAppButtonCloseAListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			viewAbout.getFrmAbout().dispose();
		}
	}
	
}
