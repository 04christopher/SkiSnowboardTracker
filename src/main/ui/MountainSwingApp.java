package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

//A class representing a TrailTracker GUI and functionality.
public class MountainSwingApp extends JFrame {

    private Mappy mappy;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: initializes fields, json writer, and frame
    public MountainSwingApp(Mappy mappy) {
        //this.mappy = mappy;
        this.mappy = createSampleMappy();
        this.jsonWriter = new JsonWriter("./data/mountain.json");
        this.jsonReader = new JsonReader("./data/mountain.json");

        initializeUI();
    }


    //MODIFIES: this
    //EFFECTS: makes the first frame
    @SuppressWarnings("methodlength")
    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mountain App GUI");
        setLayout(new BorderLayout());

        //making pannels and buttons
        JPanel mainPanel = new JPanel();
        JButton seeMountains = new JButton("See Mountains");
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");

        //i think with pack() at the bottom this doesn't matter
        setSize(2000,2000);

        //set button sizes
        seeMountains.setPreferredSize(new Dimension(300, 30));
        save.setPreferredSize(new Dimension(300, 30));
        load.setPreferredSize(new Dimension(300, 30));

        //add action listeners to buttons
        seeMountains.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMountainsFrame();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMappy();
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMappy();
            }
        });

        //adding buttons to panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(seeMountains);
        mainPanel.add(save);
        mainPanel.add(load);
        //add panels to frame
        add(addHeadPanel(), BorderLayout.PAGE_START);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Iterator eventIterator = EventLog.getInstance().iterator();

                while (eventIterator.hasNext()) {
                    Event ev = (Event) eventIterator.next();
                    System.out.println(ev.getDate() + ": " + ev.getDescription());
                }
            }
        });
    }

    //EFFECTS: saves the state of the application
    private void saveMappy() {
        try {
            this.jsonWriter.open();
            this.jsonWriter.write(this.mappy);
            this.jsonWriter.close();
            System.out.println("Saved this map to ./data/mountain.json");
        } catch (FileNotFoundException var2) {
            System.out.println("Unable to write to file: ./data/mountain.json");
        }
    }

    //EFFECTS: loads a state of mappy from json file
    public void loadMappy() {
        try {
            this.mappy = this.jsonReader.read();
            System.out.println("Loaded map from ./data/mountain.json");
        } catch (IOException var2) {
            System.out.println("Unable to read from file: ./data/mountain.json");
        }

    }

    //EFFECTS: makes the header panel and returns it
    private JPanel addHeadPanel() {
        JPanel headerPanel = new JPanel();
        LayoutManager overlay = new OverlayLayout(headerPanel);
        headerPanel.setLayout(overlay);

        //adding text label
        JLabel headerLabel = new JLabel("Trail Tracker");
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        headerLabel.setAlignmentX(0.5f);
        headerLabel.setAlignmentY(0.5f);
        headerPanel.add(headerLabel);


        // adding image
        try {
            BufferedImage myPicture =
                    ImageIO.read(new File("data/snow-mountains-blue-sky-and-lake-panoramic-web-header-.jpg"));
            Image resizedImage = myPicture.getScaledInstance(1000,250, Image.SCALE_SMOOTH);
            ImageIcon backgroundImage = new ImageIcon(resizedImage);

            JLabel comboLabel = new JLabel(backgroundImage);
            comboLabel.setAlignmentX(0.5f);
            comboLabel.setAlignmentY(0.5f);
            headerPanel.add(comboLabel);
        } catch (IOException e) {
            System.out.println("please please please please workkkkkksadlfkhfdsaj sdaflghfasdkasdfhfgdsak asdfhjg");
        }

        setVisible(true);
        return headerPanel;
    }

    //EFFECTS: makes a frame showigna  button for each mountain, and letting you add a mountain
    private void showMountainsFrame() {
        JFrame mountainsFrame = new JFrame("Mountain List");
        mountainsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close only the current frame

        //making panels
        JPanel mountainsPanel = new JPanel();
        mountainsPanel.setLayout(new BoxLayout(mountainsPanel, BoxLayout.Y_AXIS));

        // add buttonfor each mountain w/ actionlistenrer
        for (Mountain mountain : mappy.getMountains()) {
            JButton mountainButton = new JButton(mountain.getName());
            mountainButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showMountainDetailsFrame(mountain);
                }
            });
            mountainsPanel.add(mountainButton);
        }
        //add mountain button
        JButton addMountainButton = addMountainButton();
        mountainsPanel.add(addMountainButton);

        mountainsFrame.add(mountainsPanel);

        mountainsFrame.setSize(400, 300);
        mountainsFrame.setLocationRelativeTo(null); // center on screen
        mountainsFrame.setVisible(true);
    }

    //EFFECTS: returns addMountainButton
    private JButton addMountainButton() {
        JButton addMountainButton = new JButton("Add a Mountain");
        addMountainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMountainFrame(mappy);
            }
        });
        return addMountainButton;
    }

    //EFFECTS: makes frame to show all the details of a mountain
    @SuppressWarnings("methodlength")
    private void showMountainDetailsFrame(Mountain mountain) {
        JFrame mountainDetailsFrame = new JFrame(mountain.getName());
        mountainDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mountainDetailsPanel = new JPanel();
        mountainDetailsPanel.setLayout(new BoxLayout(mountainDetailsPanel, BoxLayout.Y_AXIS));
        //
        JButton seeTrailsButton = new JButton("See Trails");
        seeTrailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeTrailsAsTable(mountain);
            }
        });
        mountainDetailsPanel.add(seeTrailsButton);
        //
        JButton seeStatisticsButton = new JButton("See Statistics");
        JButton addTripButton = new JButton("Add Trip");
        JButton addTrailButton = new JButton("Add Trail");

        seeStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeStatistics(mountain);
            }
        });

        addTripButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTripFrame(mountain.getTrails(), mappy);
            }
        });

        addTrailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddTrailFrame(mountain);
            }
        });

        mountainDetailsPanel.add(seeStatisticsButton);
        mountainDetailsPanel.add(addTripButton);
        mountainDetailsPanel.add(addTrailButton);

        mountainDetailsFrame.add(mountainDetailsPanel);
        mountainDetailsFrame.setSize(400, 300);
        mountainDetailsFrame.setLocationRelativeTo(null);
        mountainDetailsFrame.setVisible(true);
    }

    //EFFECTS: makes a frame showing statistics for a mountain
    public void seeStatistics(Mountain mountain) {
        JFrame statisticsFrame = new JFrame(mountain.getName() + "statistics");
        statisticsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        statisticsFrame.setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        panel.add(new JLabel("Showing statistics for " + mountain.getName()));
        panel.add(new JLabel("Total runs done: " + mountain.getRunsDone() + "m"));
        panel.add(new JLabel("Total distance skied/snowboarded: " + mountain.getTotalDistanceSkied() + "m"));
        panel.add(new JLabel("Total number of trips: " + mountain.getNumTrips()));
        panel.add(new JLabel("Number of trails on this mountain: " + mountain.getNumTrails()));


        statisticsFrame.add(panel);
        statisticsFrame.setLocationRelativeTo(null);
        statisticsFrame.setVisible(true);
    }

    //EFFECTS: makes a frame showing all the trails as a table
    @SuppressWarnings("methodlength")
    private void seeTrailsAsTable(Mountain mountain) {
        JFrame trailsTableFrame = new JFrame("Trails Statistics: " + mountain.getName());
        trailsTableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //making table model for stats
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Trail Name");
        tableModel.addColumn("Length (m)");
        tableModel.addColumn("Descent (m)");
        tableModel.addColumn("Difficulty");
        tableModel.addColumn("Night Run");
        tableModel.addColumn("Gladed Terrain");
        tableModel.addColumn("# of Runs");
        tableModel.addColumn("Best Time");
        tableModel.addColumn("Average TIme");

        //putting in stats
        for (Trail trail : mountain.getTrails()) {
            //bug fix
            if (trail.getBestTime() == 999999999) {
                tableModel.addRow(new Object[]{
                        trail.getName(),
                        trail.getLength(),
                        trail.getDescent(),
                        Integer.toString(trail.getDifficulty()), //doing this for the renderer bc it doesn't like int
                        trail.isNightRun(),
                        trail.isGladedTerrain(),
                        trail.numTimesDone(),
                        "No Time Yet"
                });
            } else {
                tableModel.addRow(new Object[]{
                        trail.getName(),
                        trail.getLength(),
                        trail.getDescent(),
                        Integer.toString(trail.getDifficulty()),
                        trail.isNightRun(),
                        trail.isGladedTerrain(),
                        trail.numTimesDone(),
                        trail.getBestTime(),
                        trail.getAverageTime()
                });
            }
        }

        JTable trailsTable = new JTable(tableModel);

        //keep this here it can't move above trailsTable
        TableColumn difficultyColumn = trailsTable.getColumn("Difficulty");
        difficultyColumn.setCellRenderer(new DifCellRenderer());

        JScrollPane scrollPane = new JScrollPane(trailsTable);
        trailsTable.setFillsViewportHeight(true);

        trailsTableFrame.add(scrollPane);

        trailsTableFrame.setSize(800, 600);
        trailsTableFrame.setLocationRelativeTo(null);
        trailsTableFrame.setVisible(true);
    }

    //makes a starter mountain and map so that the user sees somethign
    private Mappy createSampleMappy() {
        Mappy mappy = new Mappy("The Map");

        Mountain grouse = new Mountain("Grouse");
        Trail theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        Trail purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);
        Trail peak = new Trail("Peak", 445, 40, 2, grouse, true, false);
        Trail lowerPeak = new Trail("Lower Peak", 221, 33, 2, grouse, true, true);
        Trail expoGlades = new Trail("Expo Glades", 292, 39, 3, grouse, false, true);
        mappy.addMountain(grouse);

        return mappy;
    }
}
