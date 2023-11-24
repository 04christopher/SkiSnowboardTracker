package ui;


import model.Mappy;
import model.Mountain;
import model.Trail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;

//represents a frame for adding a mountain
public class AddMountainFrame extends JFrame {
    private JTextField nameField;
    private Mappy mappy;

    public AddMountainFrame(Mappy mappy) {
        this.mappy = mappy;
        setTitle("Add Mountain");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

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

                Mountain mountain = new Mountain(name);
                mappy.addMountain(mountain);

                dispose();
            }
        });
        return submitButton;
    }
}