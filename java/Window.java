package hackathon2021;

import javax.swing.*;
import java.io.File;
import java.awt.*;

@SuppressWarnings("serial")
public class Window extends JFrame{

	final static String INTRO_ID = "intro";
	final static String DISPLAY_ID = "display";
	final static String DIRECTION_ID = "direction";
	protected File image;
	protected String zipCode;
	protected String direction;
	protected PyScript script;
	protected CardLayout cl;
	protected JPanel cardHolder;
	protected JPanel display;

	/**
	 * Builds the base of the program's window
	 */
	public Window(PyScript s){
		super();
		image = null;
		zipCode = null;
		direction = null;
		script = s;
		this.setSize(900, 500);
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
		JPanel displayCard = new JPanel(); //card used to display processed data and imaging
		JPanel directionCard = new JPanel(new BorderLayout()); //card used to display directional choices


		//instructional JLabel for user, adds to intro card
		JLabel introInstructions = new JLabel("<html><h2>Fire Detection and Prediction System</h2><hr>" +
				"<ol><li>Select File</li><li>Click Open</li><li>Select the image file you wish to detect and " +
				"predict for</li><li>Enter the Zip code and cardinal direction that the top of the image is facing</li>" +
				"</ol></html>");
		introCard.add(introInstructions);

		//placeholder text to be overwritten once a file is selected, added to display card
		JLabel displayCardHolder = new JLabel("Your image is being processed now. Please wait.");
		displayCard.add(displayCardHolder);
		display = displayCard;

		//create directions card
		DirectionChoiceHandler dch = new DirectionChoiceHandler(this);
		String[] cardinals = {"N-West", "North", "N-East", "West", "East", "S-West", "South", "S-East"};
		JButton[] directions = new JButton[8];
		for(int i = 0; i < 8; ++i) {
			directions[i] = new JButton(cardinals[i]);
			directions[i].addActionListener(dch);
		}

		JPanel buttonDisplay = new JPanel(new GridLayout(3,3));
		JPanel[][] gridStorage = new JPanel[3][3];
		for(int i = 0, it = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				gridStorage[i][j] = new JPanel();
				buttonDisplay.add(gridStorage[i][j]);
				if(!(j == 1 && i == 1)){
					gridStorage[i][j].add(directions[it]);
					++it;
				}
			}
		}
		JLabel instruct = new JLabel("Please choose which direction is at the top of the image");
		directionCard.add(instruct, BorderLayout.NORTH);
		directionCard.add(buttonDisplay, BorderLayout.CENTER);

		//to add the intro, direction, and display cards to the holding panel using INTRO_ID, DIRECTION_ID, DISPLAY_ID as keys
		cardHolder.add(introCard, INTRO_ID);
		cardHolder.add(displayCard, DISPLAY_ID);
		cardHolder.add(directionCard, DIRECTION_ID);

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