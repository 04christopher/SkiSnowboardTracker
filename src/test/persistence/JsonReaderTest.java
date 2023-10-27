package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Mappy mapped = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMappy() {
        JsonReader reader = new JsonReader("./data/emptyMappy.json");
        try {
            Mappy mapped = reader.read();
            assertEquals("British Columbia", mapped.getName());
            assertEquals(0, mapped.numMountains());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMappy() {
        JsonReader reader = new JsonReader("./data/generalMappy.json");
        try {
            Mappy mapped = reader.read();
            assertEquals("The Map", mapped.getName());
            List<Mountain> mountains = mapped.getMountains();
            assertEquals(1, mountains.size());

            ArrayList<Trail> trailsG = new ArrayList<Trail>();
            ArrayList<Trip> tripsG = new ArrayList<Trip>();
            ArrayList<Run> runsG = new ArrayList<Run>();

            Trip trip = new Trip("", runsG);

            Mountain grouse = new Mountain("grouse");
            Trail theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
            Trail purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);
            Run r1 = new Run(theCut, 208);

            trailsG.add(theCut);
            trailsG.add(purgatory);

            trip.addRun(r1);
            tripsG.add(trip);

            checkMountains("grouse",  trailsG, tripsG, mountains.get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
