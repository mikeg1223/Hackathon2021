package hackathon2021;
import javax.swing.*;
import java.io.File;
import java.awt.*;

@SuppressWarnings("serial")
public class Window extends JFrame{

	final static String INTRO_ID = "intro";
	final static String DISPLAY_ID = "display";
	protected File image;
	protected String zipCode;
	protected String direction;

	/**
	 * Builds the base of the program's window
	 */
	public Window(){
		super();
		image = null;
		zipCode = null;
		direction = null;
		this.setSize(900, 500);
		this.setTitle("Fire Detection System FDS");
		this.setDefaultCloseOperation(Window.EXIT_ON_CLOSE); //makes sure to close the window properly when clicking on 'x'

		JMenuBar bar = new JMenuBar();
		createFileMenu(bar); //calls the method below
		this.setJMenuBar(bar); //put the menu bar on the window

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
		JPanel cards = new JPanel(new CardLayout()); //panel used to hold card in a card layout.

		//instructional JLabel for user, adds to intro card
		JLabel introInstructions = new JLabel("<html><h2>Fire Detection and Prediction System</h2><hr>" +
				"<ol><li>Select File</li><li>Click Open</li><li>Select the image file you wish to detect and " +
				"predict for</li><li>Enter the Zip code and cardinal direction that the top of the image is facing</li>" +
				"</ol></html>");
		introCard.add(introInstructions);

		//placeholder text to be overwritten once a file is selected, added to display card
		JLabel displayCardHolder = new JLabel("This is a placeholder text. No data has been received yet");
		displayCard.add(displayCardHolder);

		//adding the intro and display to the holding panel using INTRO_ID and DISPLAY_ID as keys
		cards.add(introCard, INTRO_ID);
		cards.add(displayCard, DISPLAY_ID);


		pane.add(cards, BorderLayout.CENTER);
	}

	protected void swapCard(int n){

	}
	protected void clearDisplayCard(){

	}
  /*  protected static String getDirection(){
        DirectionChoiceHandler temp = new DirectionChoiceHandler();
        if(temp.answer)
        return temp.answer;
    }*/
