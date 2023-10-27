//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import model.*;
import org.json.JSONObject;

// Represents a writer that writes JSON representation of Mappy to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(this.destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Mappy to file
    public void write(Mappy mapped) {
        JSONObject json = mapped.toJson();
        this.saveToFile(json.toString(4));  // need to change this maybe
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        this.writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        this.writer.print(json);
    }
}
