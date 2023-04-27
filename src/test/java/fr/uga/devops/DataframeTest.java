package fr.uga.devops;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataframeTest {
    Dataframe data;

    @Before
    public void initialize() {
        data = new Dataframe("example.csv");
    }

    @Test
    public void testCSVConstructor() {
        assertNotNull(data);
    }

    @Test
    public void testInvalidCSVConstructor() {
        assertThrows(RuntimeException.class, () -> new Dataframe("example"));
        assertThrows(RuntimeException.class, () -> new Dataframe("example.pdf"));
        assertThrows(RuntimeException.class, () -> new Dataframe("example."));
        assertThrows(RuntimeException.class, () -> new Dataframe(".csv"));
    }

    @Test
    public void testEmptyConstructor() {
        Dataframe data = new Dataframe();
        assertNotNull(data);
    }

    @Test
    public void testRemoveColumns() {
        for (int i = 1; i <= 15; i++) {
            data.removeColumn("label" + i);
        }
        data.removeColumn("label1");
    }

    @Test
    public void testAddColumns() {
        data = new Dataframe();
        ArrayList<Object> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        data.addColumn("label", list);
    }
}
