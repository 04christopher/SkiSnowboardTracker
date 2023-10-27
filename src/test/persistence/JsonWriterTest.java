package persistence;

import model.Mappy;
import model.Mountain;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Mappy mapped = new Mappy("bc");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Mappy mapped = new Mappy("bc");
            JsonWriter writer = new JsonWriter("./data/emptyWriterMappy.json");
            writer.open();
            writer.write(mapped);
            writer.close();

            JsonReader reader = new JsonReader("./data/emptyWriterMappy.json");
            mapped = reader.read();
            assertEquals("bc", mapped.getName());
            assertEquals(0, mapped.numMountains());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Mappy mapped = new Mappy("bc");
            mapped.addMountain(new Mountain("grouse"));
            mapped.addMountain(new Mountain("whistler"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(mapped);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            mapped = reader.read();
            assertEquals("bc", mapped.getName());
            List<Mountain> mountains = mapped.getMountains();
            assertEquals(2, mountains.size());
            assertEquals("grouse", mountains.get(0).getName());
            assertTrue(mountains.get(0).getTrails().isEmpty());
            assertTrue(mountains.get(0).getTrips().isEmpty());
            assertEquals("whistler", mountains.get(1).getName());
            assertTrue(mountains.get(1).getTrails().isEmpty());
            assertTrue(mountains.get(1).getTrips().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
