package WordTrainer;
import java.awt.EventQueue;


/**
 * Initialisierung: Diese Klasse startet die Applikation Sprachtrainer - MVC
 * wird initialisiert.
 * 
 * @author Andreas Gyr
 */
public class RunMVC {

	/**
	 * Start des Programm
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Erstelle Model und View Objekte
					Model myModel = new Model();
					ViewMainFrame myView = new ViewMainFrame();

					// Erstelle Controller und f�ge Model und View hinzu
					new Controller(myModel, myView);
					boolean bool = true;
					System.out.println(bool);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
