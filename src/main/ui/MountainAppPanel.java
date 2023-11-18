package ui;

import model.Mappy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MountainAppPanel extends JPanel {
    private Mappy bc;
    private MountainApp mountainApp;

    public MountainAppPanel() {
        mountainApp = new MountainApp();  // Assuming you have a MountainApp class

        // create the main frame
        JFrame frame = new JFrame("Mountain App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // create a button
        JButton loadButton = new JButton("Load Data");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the method in your MountainApp to load data
                mountainApp.loadMappy();  // You need to implement this method
                JOptionPane.showMessageDialog(frame, "Data Loaded Successfully!");
            }
        });

        // add the button to the frame
        frame.getContentPane().add(loadButton);

        // set frame visibility
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MountainAppPanel();
            }
        });
    }
}
