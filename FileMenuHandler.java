package hackathon2021;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class FileMenuHandler implements ActionListener{
	
	Window myframe;
	
	FileMenuHandler (Window frame){			
		myframe = frame;	
	}
	
	/**
	 * Detects when user clicks on "Open" or "Quit"
	 * and performs the respective actions
	 */
	public void actionPerformed(ActionEvent ae) {	
		String eventName = ae.getActionCommand();
		if(eventName.equals("Open")) {
			openFile();
		}
		else if(eventName.equals("Quit")) {
			System.exit(0);
		}
	}

	/**
	 * Opens the small window to select file from computer
	 */
	private void openFile()	{
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		if(chooser.getSelectedFile() != null)
			readSource(chooser.getSelectedFile());
	}

	private void readSource(File chosenFile) {
		//Checking the chosen file
	}
	
}
