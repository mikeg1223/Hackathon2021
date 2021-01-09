package hackathon2021

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DirectionChoiceHandler extends JFrame implements ActionListener {

    protected String answer;
    protected Window reference;

    public DirectionChoiceHandler(Window ref){
        super();
        reference = ref;
        this.setSize(250, 250);
        this.setLocation(300,300);
        this.setTitle("Cardinal Direction Choice");
        this.setDefaultCloseOperation(Window.DISPOSE_ON_CLOSE);

        String[] cardinals = {"N-West", "North", "N-East", "West", "East", "S-West", "South", "S-East"};
        JButton[] directions = new JButton[8];
        for(int i = 0; i < 8; ++i) {
            directions[i] = new JButton(cardinals[i]);
            directions[i].addActionListener(this);
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
        JPanel frame = new JPanel(new BorderLayout());
        frame.add(instruct, BorderLayout.NORTH);
        frame.add(buttonDisplay, BorderLayout.CENTER);
        this.getContentPane().add(frame);
        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String eventName = ae.getActionCommand();
        reference.direction = eventName;
        this.dispose();
    }

}
