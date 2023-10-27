package persistence;

import model.Mountain;
import model.Run;
import model.Trail;
import model.Trip;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkMountains(String name, ArrayList<Trail> trails, ArrayList<Trip> trips, Mountain m) {
        Trail t1 = trails.get(0);
        Trail t2 = trails.get(1);
        Trip trip= trips.get(0);

        assertEquals(name, m.getName());
        checkTrail("The Cut", 1100, 201, 1, t1.getMountain(), true, false, t1);
        checkTrail("Purgatory", 538, 196, 4, t2.getMountain(), false, false, t2);
        checkTrip("",trip.getRuns(), trip);
    }

    protected void checkTrail(String name, int length, int descent, int dif, Mountain mountain, boolean n, boolean g, Trail t) {
        assertEquals(name, t.getName());
        assertEquals(length, t.getLength());
        assertEquals(descent, t.getDescent());
        assertEquals(dif, t.getDifficulty());
        assertEquals(mountain, t.getMountain());
        assertEquals(n, t.isNightRun());
        assertEquals(g, t.isGladedTerrain());
    }

    protected void checkTrip(String nameOrDate, ArrayList<Run> runs, Trip t) {
        assertEquals(nameOrDate, t.getNameOrDate());
        assertEquals(runs, t.getRuns());
    }
}
