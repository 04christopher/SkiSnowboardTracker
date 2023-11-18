package ui;

import model.Mappy;
import model.Mountain;
import model.Trail;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestingFrame extends JFrame {

    private Mappy mappy;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public TestingFrame(Mappy mappy) {
        this.mappy = mappy;

        this.jsonWriter = new JsonWriter("./data/mountain.json");
        this.jsonReader = new JsonReader("./data/mountain.json");
        initMap();
        init();
    }

    public void init() {
        JFrame frame = new JFrame("Mountain App GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mountainPanel = new JPanel();

        // Create a button for each mountain
        for (Mountain mountain : mappy.getMountains()) {
            JButton mountainButton = new JButton(mountain.getName());
            mountainButton.addActionListener(new MountainListener(mountain));
            mountainPanel.add(mountainButton);
        }

        frame.getContentPane().add(mountainPanel);
        frame.pack();
        frame.setVisible(true);

    }

    public void initMap() {
        Mountain grouse = new Mountain("Grouse Mountain");
        mappy.addMountain(grouse);
        Trail theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        Trail purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);
        Trail peak = new Trail("Peak", 445, 40, 2, grouse, true, false);
        Trail lowerPeak = new Trail("Lower Peak", 221, 33, 2, grouse, true, false);
        Trail expoGlades = new Trail("Expo Glades", 292, 39, 3, grouse, false, true);
    }
}
