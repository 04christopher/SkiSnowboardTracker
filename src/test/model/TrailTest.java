package model;


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


}