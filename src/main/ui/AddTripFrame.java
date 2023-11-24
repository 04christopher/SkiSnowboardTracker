package ui;

import model.Mappy;
import model.Run;
import model.Trail;
import model.Trip;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//a class representing a frame giving the user options to add a trip.
public class AddTripFrame extends JFrame {

    private JTextField tripNameField;
    private JTable runTable;
    private DefaultTableModel tableModel;
    private JComboBox<Trail> trailSelector;
    private Mappy mappy;

    //MODIFIES: this
    //EFFECTS: constructor, makes trip frame
    @SuppressWarnings("methodlength")
    public AddTripFrame(List<Trail> availableTrails, Mappy mappy) {
        this.mappy = mappy;
        setTitle("Add Trip");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //name
        tripNameField = new JTextField();
        JLabel nameLabel = new JLabel("Trip Name:");
        nameLabel.setLabelFor(tripNameField);

        //table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Trail");
        tableModel.addColumn("Time (s)");
        runTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(runTable);
        JLabel runsLabel = new JLabel("Runs:");

        // trail selector
        //trailSelector = new JComboBox<>(availableTrails.toArray(new Trail[0]));

        DefaultComboBoxModel<Trail> trailModel = new DefaultComboBoxModel<>(availableTrails.toArray(new Trail[0]));
        trailSelector = new JComboBox<>(trailModel);
        trailSelector.setRenderer(new TrailCellRenderer());

        JButton addButton = new JButton("Add Run");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRunToTable();
            }
        });

        //submit
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTrip();
            }
        });

        //layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tripNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(runsLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        add(new JLabel("Select Trail:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(trailSelector, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(submitButton, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //EFFECTS: adds run to the table
    private void addRunToTable() {
        Trail selectedTrail = (Trail) trailSelector.getSelectedItem();
        tableModel.addRow(new Object[]{selectedTrail.getName(), 0});
    }

    //EFFECTS: will make a trip object
    private void createTrip() {
        String name = tripNameField.getText();
        ArrayList<Run> runs = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Trail selectedTrail =  mappy.findTrailByName((String) tableModel.getValueAt(i, 0));
            //int runTime = (Integer) tableModel.getValueAt(i, 1);
            //int runTime = Integer.parseInt(runTimeStr);

            Object runTimeObj = tableModel.getValueAt(i, 1);

            try {
                int runTime;
                if (runTimeObj instanceof Integer) {
                    runTime = (Integer) runTimeObj;
                } else if (runTimeObj instanceof String) {
                    runTime = Integer.parseInt((String) runTimeObj);
                } else {
                    throw new IllegalArgumentException("Unexpected type for run time");
                }

                Run run = new Run(selectedTrail, runTime);
                runs.add(run);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        Trip trip = new Trip(name, runs);
        trip.addTripToRecord();
        dispose();
    }

}