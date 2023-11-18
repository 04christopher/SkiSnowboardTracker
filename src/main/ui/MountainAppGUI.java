package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Mountain;
import model.Run;
import model.Trail;
import model.Trip;
import model.Mappy;


public class MountainAppGUI extends JFrame {
    private Mappy mappy;

    public MountainAppGUI(Mappy mappy) {
        this.mappy = mappy;

        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mountain App GUI");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));

        for (Mountain mountain : mappy.getMountains()) {
            JButton mountainButton = new JButton(mountain.getName());
            mountainButton.addActionListener(new MountainButtonListener(mountain));
            mainPanel.add(mountainButton);
        }

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class MountainButtonListener implements ActionListener {
        private Mountain mountain;

        public MountainButtonListener(Mountain mountain) {
            this.mountain = mountain;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showTrailsForMountain(mountain);
        }
    }

    private void showTrailsForMountain(Mountain mountain) {
        JFrame trailsFrame = new JFrame("Trails for " + mountain.getName());
        trailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        trailsFrame.setLayout(new GridLayout(0, 1));

        for (Trail trail : mountain.getTrails()) {
            JButton trailButton = new JButton(trail.getName());
            trailButton.addActionListener(new TrailButtonListener(trail));
            trailsFrame.add(trailButton);
        }

        JButton recordTripButton = new JButton("Record Trip");
        recordTripButton.addActionListener(new RecordTripButtonListener(mountain));
        trailsFrame.add(recordTripButton);

        trailsFrame.pack();
        trailsFrame.setLocationRelativeTo(null);
        trailsFrame.setVisible(true);
    }

    private class TrailButtonListener implements ActionListener {
        private Trail trail;

        public TrailButtonListener(Trail trail) {
            this.trail = trail;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showTrailDetails(trail);
        }
    }

    private void showTrailDetails(Trail trail) {
        String message = "Trail Name: " + trail.getName() + "\n"
                + "Length: " + trail.getLength() + "m\n"
                + "Descent: " + trail.getDescent() + "m\n"
                + "Difficulty: " + trail.getDifficulty() + "\n"
                + "Night Run: " + trail.isNightRun() + "\n"
                + "Gladed Terrain: " + trail.isGladedTerrain() + "\n"
                + "Best Time: " + trail.getBestTime() + " seconds\n"
                + "Average Time: " + trail.getAverageTime() + " seconds\n"
                + "Number of Times Done: " + trail.getNumTimesDone() + "\n"
                + "Run Times: " + trail.getRunTimesAsFormattedTime();

        JOptionPane.showMessageDialog(this, message, "Trail Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private class RecordTripButtonListener implements ActionListener {
        private Mountain mountain;

        public RecordTripButtonListener(Mountain mountain) {
            this.mountain = mountain;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            recordTrip(mountain);
        }
    }

    private void recordTrip(Mountain mountain) {
        String tripName = JOptionPane.showInputDialog(this, "Enter a name/date for this trip:");

        if (tripName != null && !tripName.isEmpty()) {
            ArrayList<Run> runs = new ArrayList<>();
            int numRuns = Integer.parseInt(JOptionPane.showInputDialog(this, "How many runs did you do on this trip?"));

            for (int i = 0; i < numRuns; i++) {
                Run run = enterRun();
                runs.add(run);
            }

            Trip trip = new Trip(tripName, runs);
            trip.addTripToRecord();
            mountain.addTrip(trip);
            JOptionPane.showMessageDialog(this, "Trip recorded!");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid trip name!");
        }
    }

    private Run enterRun() {
        // This method should be similar to the one in your original MountainApp class
        // It will prompt the user to select a trail and enter run details
        // You can reuse the existing code for entering a run
        // ...

        // For now, I'll return a dummy run
        Trail dummyTrail = new Trail("Dummy Trail", 100, 20, 2, new Mountain("Dummy Mountain"), false, false);
        return new Run(dummyTrail, 300);
    }

    public static void main(String[] args) {
        // Replace this with your actual Mappy instance
        Mappy sampleMappy = createSampleMappy();
        SwingUtilities.invokeLater(() -> new MountainAppGUI(sampleMappy));
    }

    private static Mappy createSampleMappy() {
        Mappy mappy = new Mappy("The Map");

        Mountain grouse = new Mountain("Grouse");
        Trail theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        Trail purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);
        grouse.addTrail(theCut);
        grouse.addTrail(purgatory);
        mappy.addMountain(grouse);

        // Add more mountains and trails as needed

        return mappy;
    }
}