package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import model.Mountain;
import model.Run;
import model.Trail;
import model.Trip;
import model.Mappy;
import org.json.JSONArray;
import org.json.JSONObject;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

//Mountain teller application
public class MountainApp {

    private Scanner input;
    private Mappy bc;
    private Mountain grouse;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECT: runs the mountain app
    public MountainApp() {
        this.bc = new Mappy("The Map");
        this.jsonWriter = new JsonWriter("./data/mountain.json");
        this.jsonReader = new JsonReader("./data/mountain.json");
        runMountain();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runMountain() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
    }

    private void saveMappy() {
        try {
            this.jsonWriter.open();
            this.jsonWriter.write(this.bc);
            this.jsonWriter.close();
            System.out.println("Saved this map to ./data/mountain.json");
        } catch (FileNotFoundException var2) {
            System.out.println("Unable to write to file: ./data/mountain.json");
        }
    }

    private void loadMappy() {
        try {
            this.bc = this.jsonReader.read();
            System.out.println("Loaded map from ./data/mountain.json");
        } catch (IOException var2) {
            System.out.println("Unable to read from file: ./data/mountain.json");
        }

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("r")) {
            recordTrip();
        } else if (command.equals("s")) {
            checkStats();
        } else if (command.equals("a")) {
            addTrailToMountain();
        }  else if (command.equals("m")) {
            addMountainToMap();
        } else if (command.equals("p")) {
            seeMountains();
        } else if (command.equals("save")) {
            saveMappy();
        } else if (command.equals("load")) {
            loadMappy();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: displayes menu of options to user
    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("r -> record a trip");
        System.out.println("s -> see information and statistics");
        System.out.println("a -> add a Trail");
        System.out.println("m -> add a Mountain");
        System.out.println("p -> see mountains");
        System.out.println("save -> save the current state");
        System.out.println("load -> load a saved state");
        System.out.println("q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes grouse mountain, 5 trails, and a scanner
    public void init() {
        grouse = new Mountain("grouse");
        bc.addMountain(grouse);
        Trail theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        Trail purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);
        Trail peak = new Trail("Peak", 445, 40, 2, grouse, true, false);
        Trail lowerPeak = new Trail("Lower Peak", 221, 33, 2, grouse, true, false);
        Trail expoGlades = new Trail("Expo Glades", 292, 39, 3, grouse, false, true);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    //MODIFIES: this
    //EFFECTS: records a trip from the user and adds it to a selected mountain.
    public void recordTrip() {
        System.out.println("Enter a name/date for this trip: ");
        String nameOrDate = input.nextLine();

        input.nextLine();// this line fixes the scanner skipping

        System.out.println("Which mountain did you do this trip on?");
        Mountain matchingMountain = selectMountain();

        System.out.println("How many runs did you do on this trip?");
        int amount = input.nextInt();

        ArrayList<Run> empty = new ArrayList<Run>();
        Trip trip = new Trip(nameOrDate, empty);

        for (int i = 0; i < amount; i++) {
            trip.addRun(enterRun());
        }
        trip.addTripToRecord();
        matchingMountain.addTrip(trip);
        System.out.println("Trip recorded!");
    }

    //MODIFIES: this
    //EFFECTS: process user input for stats menu
    public void checkStats()  {
        String command = null;
        displayStatMenu();
        command = input.next();
        command = command.toLowerCase();
        processStatCommand(command);
    }

    //MODIFIES: this
    //EFFECTS: displays menu for options on statistics
    public void displayStatMenu() {
        System.out.println("What would you like to see information on?");
        System.out.println("m -> a mountain");
        System.out.println("t -> a trail");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    public void processStatCommand(String command) {
        if (command.equals("m")) {
            mountainStats();
        } else if (command.equals("t")) {
            trailStats();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //MODIFIES: this
    //EFFECTS: takes information from user and adds a trail to mountain
    //REASON FOR METHOD LENGTH: this method is long because a Trail has many fields,
    // but the function cannot be broken down.
    @SuppressWarnings("methodlength")
    public void addTrailToMountain() {
        System.out.println("What is the name of this trail?");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("What is the length of this trail? (in metres)");
        int length = input.nextInt();
        System.out.println("What is the descent of this trail? (in metres)");
        int descent = input.nextInt();
        System.out.println("\nWhat is the difficulty of this trail?");
        System.out.println("\t1 -> Green (Beginner)");
        System.out.println("\t2 -> Blue (Intermediate)");
        System.out.println("\t3 -> Black Diamond (Advanced)");
        System.out.println("\t4 -> Double Black Diamond (Expert)");
        int dif = input.nextInt();
        System.out.println("What mountain is this trail on? ");
        //leave empty for this phase
        String mountain = input.nextLine();
        Mountain matchingMountain = selectMountain();
        System.out.println("Is this trail a night run? (y/n)");
        boolean nr = false;
        input.nextLine(); //bug fix
        if (input.nextLine() == "y") {
            nr = true;
        }
        System.out.println("Is this trail gladed terrain? (y/n)");
        boolean gl = false;
        if (input.nextLine() == "y") {
            gl = true;
        }
        Trail t1 = new Trail(name, length, descent, dif, matchingMountain, nr, gl);
        System.out.println("Trail " + name + " added!");
    }

    //MODIFIES: this
    //EFFECTS: prompts the user to select a trail that exists, or add a new one
    // if no trail exists.
    //REASON FOR METHOD LENGTH: this method makes the most sense to stay together like this
    //as it is only searching for a trail that matches user input.
    @SuppressWarnings("methodlength")
    public Trail selectTrail() {
        input = new Scanner(System.in);
        boolean validSelection = false;
        Trail matchingTrail = null;

        while (!validSelection) {
            System.out.println("Enter trail: ");
            String trailName = input.nextLine();

            matchingTrail = bc.findTrailByName(trailName);
            if (matchingTrail != null) {
                validSelection = true;
            } else {
                System.out.println("No matching trail.");
                System.out.println("Would you like to add a trail? y/n");
                String yn = input.nextLine();
                if (yn.equalsIgnoreCase("y")) {
                    addTrailToMountain();
                    validSelection = true;
                } else {
                    System.out.println("Please try again.");
                }
            }
        }
        return matchingTrail;
    }

    //MODIFIES: this
    //EFFECTS: prompts the user to select a mountain that exists, or add a new one
    // if no trail exists.
    //REASON FOR METHOD LENGTH: this method makes the most sense to stay together like this
    //as it is only searching for a mountain that matches user input.
    @SuppressWarnings("methodlength")
    public Mountain selectMountain() {
        input = new Scanner(System.in);
        Mountain matchingMountain = null;
        boolean validSelection = false;

        while (!validSelection) {
            System.out.println("Enter mountain: ");
            String trailName = input.nextLine();

            for (Mountain mountain : bc.getMountains()) {
                if (mountain.getName().equalsIgnoreCase(trailName)) {
                    matchingMountain = mountain;
                    break; // exit loop when match found
                }
            }
            if (matchingMountain != null) {
                validSelection = true;
            } else {
                System.out.println("No matching mountain.");
                System.out.println("Would you like to add a mountain? y/n");
                String yn = input.nextLine();
                if (yn.equalsIgnoreCase("y")) {
                    addMountainToMap();
                    validSelection = true;
                } else {
                    System.out.println("Please try again.");
                }
            }
        }
        return matchingMountain;
    }

    //MODIFIES: this
    //EFFECTS: adds a mountain entered by user
    public void addMountainToMap() {
        System.out.println("What is the name of this Mountain?");
        input.nextLine();
        String name = input.nextLine();
        Mountain addedMountain = new Mountain(name);
        bc.addMountain(addedMountain);
        System.out.println(name + " added!");
    }

    //MODIFIES: this
    //EFFECTS: Records a run entered by the user, and translates time to seconds.
    public Run enterRun() {
        Trail matchingTrail = selectTrail();

        System.out.println("Enter time (format mm:ss, like 05:30): ");
        String timeIn = input.nextLine();
        LocalTime localTime = LocalTime.parse("00:" + timeIn);

        int min = localTime.getMinute();
        int sec = localTime.getSecond();
        int runTime = min * 60 + sec;

        Run thisRun = new Run(matchingTrail, runTime);
        System.out.println("Run on " + matchingTrail.getName() + " with time " + localTime + " recorded.");
        return thisRun;
    }

    //MODIFIES: this
    //EFFECTS: takes difficulty integer and returns the english translation
    public String translateDifficulty(int n) {
        if (n == 1) {
            return "Green (Beginner)";
        } else if (n == 2) {
            return "Blue (Intermediate)";
        } else if (n == 3) {
            return "Black Diamond (Advanced)";
        } else {
            return "Double Black Diamond (Expert)";
        }
    }

    //MODIFIES: this
    //EFFECTS: prints out statistics of a mountain
    public void mountainStats() {
        Mountain matchingMountain = selectMountain();
        System.out.println("Showing statistics for" + matchingMountain.getName());
        System.out.println("Total runs done: " + matchingMountain.getRunsDone());
        System.out.println("Total distance skied/snowboarded: " + matchingMountain.getTotalDistanceSkied());
        System.out.println("Total number of trips: " + matchingMountain.getNumTrips());
        System.out.println("List of trails on this mountain: " + matchingMountain.getTrailNames());
    }

    //MODIFIES: this
    //EFFECTS: prints out statistics of a selected trail
    public void trailStats() {
        Trail selectedTrail = selectTrail();
        System.out.println("Showing statistics for " + selectedTrail.getName());
        System.out.println("Length: " + selectedTrail.getLength() + "m");
        System.out.println("Difficulty: " + translateDifficulty(selectedTrail.getDifficulty()));
        System.out.println("Descent: " + selectedTrail.getDescent() + "m");
        String nr = "";
        if (!selectedTrail.isNightRun()) {
            nr = "not ";
        }

        String gl = "";
        if (!selectedTrail.isGladedTerrain()) {
            gl = "not ";
        }
        System.out.println("This run is " + nr + "a night run.");
        System.out.println("This run is " + gl + "gladed terrain.");
        System.out.println("Your Best Time: " + selectedTrail.getBestTime());
        System.out.println("Your Average Time: " + selectedTrail.getAverageTime());
        System.out.println("You have done this run " + selectedTrail.getNumTimesDone() + " times.");
        System.out.println("Here is a list of your run times: " + selectedTrail.getRunTimesAsFormattedTime());
    }

    public void seeMountains() {
        for (Mountain m : bc.getMountains()) {
            System.out.println(m.getName());
        }
        System.out.println("That's it!");
    }
}
