package model;

import java.util.ArrayList;

public class Mountain {
    private final String name; //name of this mountain
    private ArrayList<Trail> trails; // list of trails on a Mountain
    private ArrayList<Trip> trips;   // list of trips done on the mountain
    private int runsDone;            // total runs done on this mountain
    private int totalDistanceSkied;


    //EFFECTS: creates a mountain with name
    public Mountain(String name) {
        this.name = name;
        trails = new ArrayList<Trail>();
        trips = new ArrayList<Trip>();
        runsDone = 0;
        totalDistanceSkied = 0;
    }

    //EFFECTS: creates a mountain with a list of trails that are on that mountain, and a list of trips that a person has
    // done on that mountain
    public Mountain(String name, ArrayList<Trail> trails, ArrayList<Trip> trips) {
        this.name  = name;
        this.trails = trails;
        this.trips = trips;
        runsDone = 0;
        totalDistanceSkied = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds a trail to the mountain
    public void addTrail(Trail trail) {
        trails.add(trail);
    }

    //REQUIRES: num >= 0
    //MODIFIES: this
    //EFFECTS: adds num runs to the total number of runs
    public void addToTotalRuns(int num) {
        runsDone += num;
    }

    //REQUIRES: num >= 0
    //MODIFIES: this
    //EFFECTS: adds num to total distance skied
    public void addToTotalDistance(int num) {
        totalDistanceSkied += num;
    }

    //MODIFIES: this
    //EFFECTS: adds a trip to the list of trips done on this mountain
    public void addTrip(Trip trip) {
        trips.add(trip);
    }

    public ArrayList<Trail> getTrails() {
        return trails;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public int getRunsDone() {
        return runsDone;
    }

    public int getTotalDistanceSkied() {
        return totalDistanceSkied;
    }

    public String getName() {
        return this.name;
    }

    public int getNumTrails() {
        return trails.size();
    }

    public int getNumTrips() {
        return trips.size();
    }

    public ArrayList<String> getTrailNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Trail trail: trails) {
            names.add(trail.getName());
        }
        return names;
    }
}


