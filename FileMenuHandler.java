package hackathon2021;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.regex.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileMenuHandler implements ActionListener{

	public class IllegalFileException extends IllegalArgumentException{
		public IllegalFileException(String message){
			super(message);
		}
	}
	
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
			try {
				openFile();
			}catch(IllegalFileException e){
				JOptionPane.showMessageDialog(null,e.getMessage());
			}
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
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and JPEG FILE TYPES", "jpeg", "jpg");
		chooser.setFileFilter(filter);
		int retVal = chooser.showOpenDialog(null);
		if(retVal == JFileChooser.APPROVE_OPTION)
			myframe.image = chooser.getSelectedFile();
		else if (retVal == JFileChooser.ERROR_OPTION)
			throw new IllegalFileException("File Error, ERROR_OPTION Flagged");
		String zip = JOptionPane.showInputDialog(null,"Please enter the zip code for this location");
		Pattern p;
		Matcher m;
		String zipPattern = "^(\\d{5})(-\\d{4})?$";
		p = Pattern.compile(zipPattern);
		m = p.matcher(zip);
		while(!m.matches() && !zip.isEmpty()){
			zip = JOptionPane.showInputDialog(null,"Invalid Zip, Please enter the zip code for this location, blank for" +
					" exit");
			m = p.matcher(zip);
		}
		if(!zip.isEmpty()){
			myframe.zipCode = zip;
		}

	}
	
}
