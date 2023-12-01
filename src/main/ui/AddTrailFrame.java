package ui;

import model.Mappy;
import model.Mountain;
import model.Trail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

//represents a frame for adding a trail
public class AddTrailFrame extends JFrame {
    private JTextField nameField;
    private JTextField lengthField;
    private JTextField descentField;
    private JTextField difficultyField;
    private JCheckBox nightRunCheckBox;
    private JCheckBox gladedTerrainCheckBox;
    private Mountain mountain;

    //EFFECTS: makes a add trail frame
    @SuppressWarnings("methodlength")
    public AddTrailFrame(Mountain mountain) {
        this.mountain = mountain;
        setTitle("Add Trail");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //create a panel to hold the form components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        //add components to the panel
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Length (m):"));
        lengthField = new JTextField();
        panel.add(lengthField);

        panel.add(new JLabel("Descent (m):"));
        descentField = new JTextField();
        panel.add(descentField);

        panel.add(new JLabel("Difficulty (Enter 1-4):"));
        difficultyField = new JTextField();
        panel.add(difficultyField);

        panel.add(new JLabel("Night Run:"));
        nightRunCheckBox = new JCheckBox();
        panel.add(nightRunCheckBox);

        panel.add(new JLabel("Gladed Terrain:"));
        gladedTerrainCheckBox = new JCheckBox();
        panel.add(gladedTerrainCheckBox);


        JButton submitButton = makeButton();
        panel.add(submitButton);
        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton makeButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int length = Integer.parseInt(lengthField.getText());
                int descent = Integer.parseInt(descentField.getText());
                int difficulty = Integer.parseInt(difficultyField.getText());
                boolean nightRun = nightRunCheckBox.isSelected();
                boolean gladedTerrain = gladedTerrainCheckBox.isSelected();

                Trail newTrail = new Trail(name, length, descent, difficulty, mountain, nightRun, gladedTerrain);

                dispose();
            }
        });
        return submitButton;
    }
}