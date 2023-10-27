package model;

import org.json.JSONObject;

public class Run {
    private final Trail trail;
    private final int time;

    public Run(Trail trail, int time) {
        this.trail = trail;
        this.time = time;
    }

    public Trail getTrail() {
        return trail;
    }

    public int getTime() {
        return time;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("trail", this.trail.getName());
        json.put("time", this.time);
        return json;
    }
}
