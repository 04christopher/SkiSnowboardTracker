//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;


import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public Mappy read() throws IOException {
        String jsonData = this.readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return this.parseMappy(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);

        try {
            stream.forEach((s) -> {
                contentBuilder.append(s);
            });
        } catch (Throwable var7) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }
            }

            throw var7;
        }

        if (stream != null) {
            stream.close();
        }

        return contentBuilder.toString();
    }

    private Mappy parseMappy(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Mappy mappy = new Mappy(name);
        this.addMountains(mappy, jsonObject);
        return mappy;
    }

    private void addMountains(Mappy m, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("mountains");
        Iterator var4 = jsonArray.iterator();
        while (var4.hasNext()) {
            Object json = var4.next();
            JSONObject nextMountain = (JSONObject) json;
            this.addMountain(m, nextMountain);
        }
    }

    // MODIFIES: mappy
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addMountain(Mappy m, JSONObject mountainObject) {
        String name = mountainObject.getString("name");
        int runsDone = mountainObject.getInt("runsDone");
        int totalDistanceSkied = mountainObject.getInt("totalDistanceSkied");

        Mountain mountain = new Mountain(name);
        mountain.setRunsDone(runsDone);
        mountain.setTotalDistanceSkied(totalDistanceSkied);
        m.addMountain(mountain);

        // Create trails for the mountain and add them to the mountain
        addTrails(mountain, mountainObject.getJSONArray("trails"));
        addTrips(m, mountain, mountainObject.getJSONArray("trips"));
    }

    private void addTrails(Mountain mountain, JSONArray trailsArray) {
        for (Object trailObj : trailsArray) {
            JSONObject trailJson = (JSONObject) trailObj;
            String name = trailJson.getString("name");
            int length = trailJson.getInt("length");
            int descent = trailJson.getInt("descent");
            int difficulty = trailJson.getInt("difficulty");
            boolean nightrun = trailJson.getBoolean("nightrun");
            boolean gladed = trailJson.getBoolean("gladed");
            Trail trail = new Trail(name, length, descent, difficulty, mountain, nightrun, gladed);
        }
    }

    private void addTrips(Mappy m, Mountain mountain, JSONArray tripsArray) {
        for (Object tripObj : tripsArray) {
            JSONObject tripJson = (JSONObject) tripObj;
            String nameOrDate = tripJson.getString("nameOrDate");
            JSONArray runsArray = tripJson.getJSONArray("runs");
            Trip trip = new Trip(nameOrDate, new ArrayList<>());
            addRuns(m, trip, runsArray);
            mountain.addTrip(trip);
        }
    }

    private void addRuns(Mappy m, Trip trip, JSONArray runsArray) {
        for (Object runObj : runsArray) {
            JSONObject runJson = (JSONObject) runObj;
            String trailName = runJson.getString("trail");
            int time = runJson.getInt("time");
            // Find the Trail object by name (trailName) in the mountain
            Trail matchingTrail = m.findTrailByName(trailName);
            if (matchingTrail != null) {
                Run run = new Run(matchingTrail, time);
                trip.addRun(run);
            }
        }
    }
}