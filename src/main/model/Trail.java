package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Trail {
    private final String name; //name of trail
    private final Mountain mountain; //the mountain this run is on

    private final int length; //in m
    private final int difficulty; //green, blue, black, double black
    private final int descent;

    private ArrayList<Integer> runTimes;
    private int bestTime; //in minutes and seconds

    private final boolean nightRun;         // boolean expression of whether this run has certain features
    private final boolean gladedTerrain;    //

    /*
    EFFECTS: constructs a ski trail with length in km, difficulty (represented by integers 1 - 4), and mountain
     */
    public Trail(String name, int length, int descent, int dif, Mountain mountain, boolean n, boolean g) {
        this.name = name;
        this.length = length;
        this.descent = descent;
        this.difficulty = dif;
        this.mountain = mountain;
        mountain.addTrail(this);

        runTimes = new ArrayList<Integer>();

        nightRun = n;
        gladedTerrain = g;
        bestTime = 999999999;

    }

    // REQUIRES: runTimes is not empty
    // EFFECTS: computes average run time and returns it
    public int getAverageTime() {
        int sum = 0;

        for (int time : runTimes) {
            sum += time;
        }
        return (sum / runTimes.size());
    }

    //MODIFIES: this
    //EFFECTS: adds a run time to runTimes, and updates best time if needed
    public void addRunTime(int time) {
        runTimes.add(time);
        if (time < bestTime) {
            bestTime = time;
        }
    }

    //EFFECTS: returns the number of times you have done this trail
    public int numTimesDone() {
        return runTimes.size();
    }

    public ArrayList<Integer> getRunTimes() {
        return runTimes;
    }

    public int getLength() {
        return length;
    }

    public int getDescent() {
        return this.descent;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Mountain getMountain() {
        return mountain;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRunTimesAsFormattedTime() {
        ArrayList<String> formattedTimes = new ArrayList<>();
        for (int seconds : runTimes) {
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            String formattedTime = minutes + ":" + remainingSeconds;
            if (remainingSeconds < 10) {
                formattedTime = minutes + ":0" + remainingSeconds;
            }
            formattedTimes.add(formattedTime);
        }
        return formattedTimes;
    }

    public boolean isNightRun() {
        return nightRun;
    }

    public boolean isGladedTerrain() {
        return gladedTerrain;
    }

    public int getBestTime() {
        return bestTime;
    }

    public int getNumTimesDone() {
        return runTimes.size();
    }

    public boolean haveDoneBefore() {
        return !runTimes.isEmpty();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("length", this.length);
        json.put("descent", this.descent);
        json.put("difficulty", this.difficulty);
        json.put("nightrun", this.nightRun);
        json.put("gladed", this.gladedTerrain);
        json.put("bestTime", this.bestTime);

        JSONArray runTimesArray = new JSONArray(this.runTimes);
        json.put("runTimes", runTimesArray);

        return json;
    }
}