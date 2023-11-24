package ui;

import model.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // MountainApp();
        Mappy initMap = new Mappy("BC");
        new MountainSwingApp(initMap);
    }
}
