package hackathon2021;
import javax.swing.*;

@SuppressWarnings("serial")
public class Window extends JFrame{
	/**
	 * Builds the base of the program's window 
	 */
	public Window(){
		super();
		this.setSize(900, 500);
		this.setTitle("Fire Detection System FDS");
		this.setDefaultCloseOperation(Window.EXIT_ON_CLOSE); //makes sure to close the window properly when clicking on 'x'
		
		JMenuBar bar = new JMenuBar();
		createFileMenu(bar); //calls the method below
		this.setJMenuBar(bar); //put the menu bar on the window
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

}
