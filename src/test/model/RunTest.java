package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunTest {
    private Mountain grouse;
    private Trip vaycay;
    private Trail theCut;
    private Trail purgatory;
    private Run r1;
    private Run r2;

    @BeforeEach
    public void runBefore(){
        grouse = new Mountain("Grouse Mountain");

        theCut = new Trail("The Cut", 1100, 201, 1, grouse, true, false);
        purgatory = new Trail("Purgatory", 538, 196, 4, grouse, false, false);

        r1 = new Run(theCut, 300);
        r2 = new Run(purgatory, 600);

        ArrayList<Run> runs = new ArrayList<Run>();
        vaycay = new Trip("Vacation!", runs);
    }

    @Test
    public void testConstructor(){
        assertEquals(theCut, r1.getTrail());
        assertEquals(300,r1.getTime());
    }
}
