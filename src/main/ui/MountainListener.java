package ui;

import model.Mountain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MountainListener implements ActionListener {

    private Mountain mountain;

    public MountainListener(Mountain mountain) {
        this.mountain = mountain;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the button click for the specific mountain
        System.out.println("Button clicked for mountain: " + mountain.getName());
        // Add your logic to show more details or perform an action related to the selected mountain

}
