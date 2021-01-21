package hackathon2021

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DirectionChoiceHandler implements ActionListener {

    protected Window reference;

    public DirectionChoiceHandler(Window ref){
        super();
        reference = ref;
    }

    public void actionPerformed(ActionEvent ae) {
        String eventName = ae.getActionCommand();
        reference.direction = eventName;
        reference.swapCard(Window.DISPLAY_ID);
        reference.setSize(900, 500);
        reference.script.run();
    }

}
