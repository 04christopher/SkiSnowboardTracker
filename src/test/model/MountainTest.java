package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MountainTest   {

    private Mountain grouse;
    private Trail theCut;
    private Trail purgatory;
    private Trip t1;

    @BeforeEach
    public void runBefore() {
        //initialize empty lists
        ArrayList<Trail> listOfTrails = new ArrayList<Trail>();
        ArrayList<Trip> listOfTrips = new ArrayList<Trip>();

        //initialize Grouse mountain
        grouse = new Mountain("Grouse Mountain", listOfTrails, listOfTrips);

        theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);

        Run r1 = new Run(theCut, 300);
        Run r2 = new Run(purgatory, 600);

        ArrayList<Run> runs = new ArrayList<Run>();
        t1 = new Trip("Saturday Trip", runs);
        t1.addRun(r1);
        t1.addRun(r2);
    }

    @Test
    void testConstructor() {
        assertEquals("Grouse Mountain", grouse.getName());
        ArrayList<Trail> trails = new ArrayList<>();
        trails.add(theCut);
        trails.add(purgatory);
        assertEquals(trails, grouse.getTrails());
        assertEquals(2, grouse.getNumTrails());
        assertTrue(grouse.getTrips().isEmpty());
        assertEquals(0, grouse.getRunsDone());
        assertEquals(0, grouse.getTotalDistanceSkied());
    }

    @Test
    void testAddTrip() {
        grouse.addTrip(t1);
        assertEquals(t1, grouse.getTrips().get(0));
        assertEquals(1, grouse.getNumTrips());
    }

    @Test
    void testAddToTotalDistance() {
        grouse.addToTotalDistance(50);
        assertEquals(50, grouse.getTotalDistanceSkied());
    }

    @Test
    void testAddToTotalRuns() {
        grouse.addToTotalRuns(1);
        assertEquals(1, grouse.getRunsDone());
    }
}
