import javax.swing.*;
import java.awt.*;
import java.io.*;


public class PyScript{

    final protected String pythonFDetection = "fire_detection.py ";
    final protected String pythonHSDetection = "hotspot_detection.py ";
    protected String pythonVenv;
    protected String filename;
    protected String verdict1;
    protected String verdict2;
    protected Window myframe;

    public PyScript(){
        filename = "";
        verdict1 = "";
        verdict2 = "";
        pythonVenv = "";
    }
    public void setMyFrame(Window mf){
        myframe = mf;
    }
    public void run(){
        String path = System.getProperty("user.dir");
        System.out.println(path);
        myframe.swapCard(Window.DISPLAY_ID);
        path += "\\..\\python\\";
        // pythonVenv = "source " + path + "venv\\Scripts\\activate";
        pythonVenv = "venv\\Scripts\\activate.bat";
        String coords = " "+myframe.latitude + " " + myframe.longitude;
        String date = " " + myframe.imageDate;
        if(filename.isEmpty())
            throw new IllegalArgumentException("File is empty");
        try {
            System.out.println(path + pythonVenv + " && python " + path + pythonFDetection + filename + coords + date);
            Process p = Runtime.getRuntime().exec(path + pythonVenv + " && python " + path + pythonFDetection + filename + coords + date);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            verdict1 = in.readLine();
            System.out.println(verdict1);
            if (verdict1.equals("NO FIRE")) {
                p = Runtime.getRuntime().exec(path + pythonVenv + " && python " + path + pythonHSDetection + coords + date);
                in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                verdict2 = in.readLine();
                System.out.println(verdict2);
            }
            myframe.clearDisplayCard();
            JLabel label = new JLabel(verdict1 + " " + verdict2);
            label.setFont(new Font("Sans-Serif", Font.BOLD, 48));
            label.setForeground(Color.red);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            myframe.display.add(label, BorderLayout.NORTH);
            myframe.display.add(new JLabel(new ImageIcon(filename)), BorderLayout.CENTER);
            myframe.pack();
        }catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            myframe.swapCard(Window.INTRO_ID);
        }
    }
    public void setFilename(String f){
        filename = f;
    }
}
