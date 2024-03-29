package model;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrailTest {

    private Trail theCut;
    private Mountain grouse;

    @BeforeEach
    void runBefore() {
        ArrayList<Trail> listOfTrails = new ArrayList<Trail>();
        ArrayList<Trip> listOfTrips = new ArrayList<Trip>();


        grouse = new Mountain("Grouse Mountain", listOfTrails, listOfTrips);
        theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
    }

    @Test
    void testConstructor(){
        assertEquals("The Cut", theCut.getName());
        assertEquals(1100, theCut.getLength());
        assertEquals(201, theCut.getDescent());
        assertEquals(1, theCut.getDifficulty());
        assertEquals(grouse, theCut.getMountain());
        assertTrue(theCut.isNightRun());
        assertFalse(theCut.isGladedTerrain());

        assertEquals(theCut, grouse.getTrails().get(0));
        assertEquals(1, grouse.getNumTrails());
        assertEquals(0, theCut.getNumTimesDone());
    }

    @Test
    void testAddRunTimeOnce() {
        theCut.addRunTime(300);
        assertEquals(300, theCut.getRunTimes().get(0));
        assertEquals(1, theCut.getNumTimesDone());
        assertEquals(300, theCut.getBestTime());
    }

    @Test
    void testAddRunTimeMultiple() {
        ArrayList<Integer> testList = new ArrayList<Integer>();

        theCut.addRunTime(300);
        theCut.addRunTime(200);
        theCut.addRunTime(300);
        theCut.addRunTime(250);

        testList.add(300);
        testList.add(200);
        testList.add(300);
        testList.add(250);
        assertEquals(testList, theCut.getRunTimes());
        assertEquals(200, theCut.getBestTime());
    }

    @Test
    void testGetAverageTime(){
        theCut.addRunTime(300);
        theCut.addRunTime(600);
        theCut.addRunTime(100);
        theCut.addRunTime(200);

        assertEquals(300, theCut.getAverageTime());
    }

    @Test
    void testGetBestTime(){
        theCut.addRunTime(300);
        theCut.addRunTime(600);
        theCut.addRunTime(100);
        theCut.addRunTime(200);

        assertEquals(100, theCut.getBestTime());
    }

    @Test
    void testGetRunTimesAsFormattedTime(){
        ArrayList<String> testList = new ArrayList<String>();

        theCut.addRunTime(300);
        theCut.addRunTime(200);
        theCut.addRunTime(300);
        theCut.addRunTime(250);

        testList.add("5:00");
        testList.add("3:20");
        testList.add("5:00");
        testList.add("4:10");
        assertEquals(testList, theCut.getRunTimesAsFormattedTime());
    }

    @Test
    void testGetNumTimesDone(){
        theCut.addRunTime(300);
        theCut.addRunTime(200);
        theCut.addRunTime(300);
        theCut.addRunTime(250);

        assertEquals(4, theCut.getNumTimesDone());
    }

    @Test
    void testHaveDoneBefore(){
        assertFalse(theCut.haveDoneBefore());
        theCut.addRunTime(300);
        theCut.addRunTime(200);
        theCut.addRunTime(300);
        theCut.addRunTime(250);

        assertTrue(theCut.haveDoneBefore());
    }

    @Test
    void testCompiles() {
        JSONObject json = theCut.toJson();
        assertTrue(!(json == null));
    }
}