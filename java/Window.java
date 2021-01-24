<<<<<<< HEAD
// package javaClasses;
=======
package java;
>>>>>>> 308059aa0e5a0234c25ee50015ef1532999f8128

import javax.swing.*;
import java.io.File;
import java.awt.*;

@SuppressWarnings("serial")
public class Window extends JFrame{

	final static String INTRO_ID = "intro";
	final static String DISPLAY_ID = "display";
	protected File image;
	protected String imageDate;
	protected PyScript script;
	protected String latitude;
	protected String longitude;
	protected CardLayout cl;
	protected JPanel cardHolder;
	protected JPanel display;

	/**
	 * Builds the base of the program's window
	 */
	public Window(PyScript s){
		super();
		image = null;
		latitude = null;
		longitude = null;
		imageDate=null;
		script = s;
		s.setMyFrame(this);
		this.setSize(500, 300);
		this.setLocation(200,200);
		this.setTitle("Fire Detection System FDS");
		this.setDefaultCloseOperation(Window.EXIT_ON_CLOSE); //makes sure to close the window properly when clicking on 'x'

		JMenuBar bar = new JMenuBar();
		createFileMenu(bar); //calls the method below
		this.setJMenuBar(bar); //put the menu bar on the window

		cl = new CardLayout();
		cardHolder = new JPanel(cl); //panel used to hold card in a card layout.
		createCardLayout(this.getContentPane());

		this.setVisible(true);
	}


	private void createFileMenu(JMenuBar bar){
		JMenu file = new JMenu("File"); //name of the menu
		JMenuItem open, quit;
		file.add(open = new JMenuItem("Open")); //name of the items of the menu
		file.add(quit = new JMenuItem("Quit"));
		FileMenuHandler fmh = new FileMenuHandler(this); //creating a listener to handle clicks of these items
		open.addActionListener(fmh);
		quit.addActionListener(fmh);
		bar.add(file); //adds File to the menu bar
	}

	private void createCardLayout(Container pane){
		JPanel introCard = new JPanel(); //card used to display the introductory instructions
		JPanel displayCard = new JPanel(new BorderLayout(70,70)); //card used to display processed data and imaging


		//instructional JLabel for user, adds to intro card
		JLabel introInstructions = new JLabel("<html><h2>Fire Detection and Prediction System</h2><hr>" +
				"<ol><li>Select File</li><li>Click Open</li><li>Select the image file you wish to detect and " +
				"predict for</li><li>Enter the latitude and longitude for the image</li><li>Add the date for the image</li>" +
				"</ol></html>");
		introCard.add(introInstructions);

		//placeholder text to be overwritten once a file is selected, added to display card
		JLabel displayCardHolder = new JLabel("Your image is being processed now. Please wait.");
		displayCard.add(displayCardHolder, BorderLayout.CENTER);
		display = displayCard;

		//to add the intro, direction, and display cards to the holding panel using INTRO_ID, DIRECTION_ID, DISPLAY_ID as keys
		cardHolder.add(introCard, INTRO_ID);
		cardHolder.add(displayCard, DISPLAY_ID);

		//to add holding pane to frame
		pane.add(cardHolder, BorderLayout.CENTER);
	}

	protected void swapCard(String n){
		cl.show(cardHolder, n);
	}
	protected void clearDisplayCard(){
		display.removeAll();
		display.revalidate();
		display.repaint();
	}

}
}