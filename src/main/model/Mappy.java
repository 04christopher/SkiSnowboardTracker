package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

//This class is a map, containing all mountains that exist.
public class Mappy {
    private ArrayList<Mountain> mountains;
    private String name;

    //EFFECTS: constructs a map with a name;
    public Mappy(String name) {
        this.mountains = new ArrayList<Mountain>();
        this.name = name;
    }

    public ArrayList<Mountain> getMountains() {
        return this.mountains;
    }

    public String getName() {
        return name;
    }

    public void addMountain(Mountain mountain) {
        mountains.add(mountain);
        //logging event
        Event e = new Event("Added mountain " + mountain.getName() + " to map.");
        EventLog.getInstance().logEvent(e);
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("mountains", this.mountainsToJson());
        return json;
    }

    private JSONArray mountainsToJson() {
        JSONArray jsonArray = new JSONArray();
        Iterator var2 = this.mountains.iterator();

        while (var2.hasNext()) {
            Mountain m = (Mountain)var2.next();
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: takes a name returns a trail object that exists on the map
    public Trail findTrailByName(String trailName) {
        for (Mountain m : mountains) {
            for (Trail trail : m.getTrails()) {
                if (trail.getName().equalsIgnoreCase(trailName)) {
                    return trail;  //trail found
                }
            }
        }
        return null;  //cant find trail
    }

    public int numMountains() {
        return mountains.size();
    }
}
