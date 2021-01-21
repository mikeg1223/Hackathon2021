package hackathon2021;


import java.awt.event.*;
import javax.swing.*;
import java.util.regex.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileMenuHandler implements ActionListener{

	/**
	 * This internal class is for exception handling in the case that an incorrect file is opened
	 */
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
	 * Opens the small window to select file from computer, also prompts the user for location data
	 */
	private void openFile()	{
		JFileChooser chooser = new JFileChooser();

		//to restrict to jpg and jpeg file types
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG and JPEG FILE TYPES", "jpeg", "jpg");
		chooser.setFileFilter(filter);

		//to verify an image was read properly
		int retVal = chooser.showOpenDialog(null);
		if(retVal == JFileChooser.CANCEL_OPTION)
			return;
		else if (retVal == JFileChooser.ERROR_OPTION)
			throw new IllegalFileException("File Error, ERROR_OPTION Flagged");

		//to obtain location data and verify format for proper zip codes
		String zip = JOptionPane.showInputDialog(null,"Please enter the zip code for this location");
		if (zip != null) {
			Pattern p;
			Matcher m;
			String zipPattern = "^(\\d{5})(-\\d{4})?$";
			p = Pattern.compile(zipPattern);
			m = p.matcher(zip);

			//To ensure proper zipcode format is entered
			while (!m.matches() && !zip.isEmpty()) {
				zip = JOptionPane.showInputDialog(null, "Invalid Zip, Please enter the zip code for this location, blank for" +
						" exit");
				m = p.matcher(zip);
			}
			//to add the file and zip provided they are both valid
			if (retVal == JFileChooser.APPROVE_OPTION && !zip.isEmpty()) {
				myframe.image = chooser.getSelectedFile();
				myframe.zipCode = zip;
				myframe.script.setFilename(myframe.image.getAbsolutePath());
				myframe.swapCard(myframe.DIRECTION_ID);
				myframe.pack();
			}
		}
	}
}
