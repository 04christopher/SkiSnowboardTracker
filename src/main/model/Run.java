package model;

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

}
