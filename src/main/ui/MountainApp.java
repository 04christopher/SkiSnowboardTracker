package ui;

import java.time.LocalTime;
import model.Mountain;
import model.Run;
import model.Trail;
import model.Trip;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MountainApp {

    private Scanner input;
    private Mountain grouse;


    public MountainApp() {
        runMountain();
    }


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

    private void processCommand(String command) {
        if (command.equals("r")) {
            recordTrip();
        } else if (command.equals("s")) {
            checkStats();
        } else if (command.equals("a")) {
            addTrailToMountain();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("r -> record a trip");
        System.out.println("s -> see information and statistics");
        System.out.println("a -> add a Trail");
        System.out.println("q -> quit");
    }

    public void init() {
        grouse = new Mountain("Grouse Mountain");
        Trail theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        Trail purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);
        Trail peak = new Trail("Peak", 445, 40, 2, grouse, true, false);
        Trail lowerPeak = new Trail("Lower Peak", 221, 33, 2, grouse, true, false);
        Trail expoGlades = new Trail("Expo Glades", 292, 39, 3, grouse, false, true);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    public void recordTrip() {
        System.out.println("Enter a name/date for this trip: ");
        String nameOrDate = input.nextLine();

        input.nextLine();// this line fixes the scanner skipping

        System.out.println("How many runs did you do on this trip?");
        int amount = input.nextInt();

        ArrayList<Run> empty = new ArrayList<Run>();
        Trip trip = new Trip(nameOrDate, empty);

        for (int i = 0; i < amount; i++) {
            trip.addRun(enterRun());
        }
        trip.addTripToRecord();
        System.out.println("Trip recorded!");
    }

    public void checkStats()  {
        String command = null;
        displayStatMenu();
        command = input.next();
        command = command.toLowerCase();
        processStatCommand(command);
    }

    public void displayStatMenu() {
        System.out.println("What would you like to see information on?");
        System.out.println("m -> a mountain");
        System.out.println("t -> a trail");
    }

    public void processStatCommand(String command) {
        if (command.equals("m")) {
            mountainStats();
        } else if (command.equals("t")) {
            trailStats();
        } else {
            System.out.println("Selection not valid...");
        }
    }

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
        System.out.println("Pretend you answered Grouse. next!");
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
        Trail t1 = new Trail(name, length, descent, dif, grouse, nr, gl);
        System.out.println("Trail " + name + " added!");
    }

    @SuppressWarnings("methodlength")
    public Trail selectTrail() {
        input = new Scanner(System.in);

        System.out.println("Enter trail: ");
        String trailName = input.nextLine();
        Trail matchingTrail = null;
        boolean validSelection = false;

        while (!validSelection) {

            for (Trail trail : grouse.getTrails()) {
                if (trail.getName().equalsIgnoreCase(trailName)) {
                    matchingTrail = trail;
                    break; // exit loop when match found
                }
            }

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

    public void printTrail(Trail trail) {
        System.out.println("Showing stats for " + trail.getName() + ":");
        System.out.println("Length: " + trail.getLength() + "m");
        System.out.println("Difficulty: " + translateDifficulty(trail.getDifficulty()));
        System.out.println();
        System.out.println();
        System.out.println();
    }

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

    public void mountainStats() {
        System.out.println("Showing statistics for" + grouse.getName());
        System.out.println("Total runs done: " + grouse.getRunsDone());
        System.out.println("Total distance skied/snowboarded: " + grouse.getTotalDistanceSkied());
        System.out.println("Total number of trips: " + grouse.getNumTrips());
        System.out.println("List of trails on this mountain: " + grouse.getTrailNames());
    }

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
}