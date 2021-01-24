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

		Pattern p;
		String latLonPattern = "^(-)?(\\d*\\.\\d*)$";
		p = Pattern.compile(latLonPattern);

		String lat = JOptionPane.showInputDialog(null,"Please enter the Latitude this location");
		if(lat != null){
			Matcher m;
			m = p.matcher(lat);
			while(!m.matches()){
				lat = JOptionPane.showInputDialog(null, "Invalid latitude, Please enter the latitude for this location, blank for" +
						" exit");
				m = p.matcher(lat);
			}
			if(lat.isEmpty())
				return;
		}
		else
			return;

		String lon = JOptionPane.showInputDialog(null,"Please enter the Longitude for this location");
		if(lon != null) {
			Matcher n;
			n = p.matcher(lon);

			while (!n.matches()) {
				lon = JOptionPane.showInputDialog(null, "Invalid longitude, Please enter the longitude for this location, blank for" +
						" exit");
				n = p.matcher(lon);
			}
			if(lon.isEmpty())
				return;
		}
		else
			return;
		//to add the file and zip provided they are both valid
		if (retVal == JFileChooser.APPROVE_OPTION && !lat.isEmpty() && !lon.isEmpty()) {
			myframe.image = chooser.getSelectedFile();
			myframe.longitude = lon;
			myframe.latitude = lat;
			myframe.script.setFilename(myframe.image.getAbsolutePath());
			myframe.script.run();
		}
	}
}
