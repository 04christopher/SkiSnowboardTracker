package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

//Represents a list of runs, AKA a trip that a user has done. Has a name and a list of runs.
public class Trip {

    private final String nameOrDate;
    private ArrayList<Run> runs;

    //EFFECTS: creates run with name nameOrDate, and a list of runs
    public Trip(String nameOrDate, ArrayList<Run> runs) {
        this.runs = runs;
        this.nameOrDate = nameOrDate;
    }

    //EFFECTS: gets the total distance travelled between all runs this trip.
    public int distanceTravelled() {
        int totalDistance = 0;
        for (Run run : runs) {
            totalDistance += run.getTrail().getLength();
        }
        return totalDistance;
    }


    //MODIFIES: Trail (all of the trails in this trip)
    //EFFECTS: adds the time of each run to each trail's list of run times,
    //         adds the total distance of the trip
    public void addTripToRecord() {
        for (Run run : runs) {
            run.getTrail().addRunTime(run.getTime());
            run.getTrail().getMountain().addToTotalRuns(1);
            run.getTrail().getMountain().addToTotalDistance(run.getTrail().getLength());
        }
    }

    public void addRun(Run run) {
        runs.add(run);
    }

    public ArrayList<Run> getRuns() {
        return runs;
    }

    public int getRunsDone() {
        return runs.size();
    }

    public String getNameOrDate() {
        return nameOrDate;
    }

    //EFFECTS: creates a json object of trip
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("nameOrDate", this.nameOrDate);
        json.put("runs", runsToJson());
        return json;
    }

    //EFFECTS: converts run array to json objects of run
    private JSONArray runsToJson() {
        JSONArray jsonArray = new JSONArray();
        Iterator var2 = this.runs.iterator();

        while (var2.hasNext()) {
            Run r = (Run) var2.next();
            jsonArray.put(r.toJson());
        }
        return jsonArray;
    }
}
