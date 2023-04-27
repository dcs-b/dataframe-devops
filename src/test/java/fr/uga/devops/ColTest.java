package fr.uga.devops;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class ColTest {
    String label;
    ArrayList<Object> data;

    @Before
    public void initialize() {
        label = "test";
        data = new ArrayList<>();
    }

    @Test
    public void testConstructor() {
        // Test that constructor initializes the label and elements list correctly
        data.add(1);
        data.add(2);
        Col col = new Col(label, data);
        assertEquals(label, col.getLabel());
        assertEquals(1, col.getElem(0));
        assertEquals(dataType.INT, col.getType());
    }

    @Test
    public void testEmptyConstructor() {
        // Test that an exception is thrown when an empty list is provided to the constructor
        assertThrows(RuntimeException.class, () -> new Col(label, data));
    }

    @Test
    public void testInvalidDataType() {
        // Test that an exception is thrown when the data contains an unrecognized data type
        data.add("string");
        data.add(1);
        Col col = new Col(label, data);
        assertNull(col.getElem(1));
    }

    @Test
    public void testGetType() {
        // Test that getType method returns the correct data type
        data.add(1);
        Col col = new Col(label, data);
        assertEquals(dataType.INT, col.getType());

        data = new ArrayList<>();
        data.add(1.f);
        col = new Col(label, data);
        assertEquals(dataType.FLOAT, col.getType());

        data = new ArrayList<>();
        data.add(1.0);
        col = new Col(label, data);
        assertEquals(dataType.DOUBLE, col.getType());

        data = new ArrayList<>();
        data.add(true);
        col = new Col(label, data);
        assertEquals(dataType.BOOL, col.getType());

        data = new ArrayList<>();
        data.add("test");
        col = new Col(label, data);
        assertEquals(dataType.STRING, col.getType());
    }

    @Test
    public void testGetSize() {
        // Test that getType method returns the correct data type
        String label = "test";
        ArrayList<Object> data = new ArrayList<>();
        data.add(1);
        data.add(3);
        data.add(1);
        Col col = new Col(label, data);
        assertEquals(3, col.getSize());
    }

    @Test
    public void testGetLabel() {
        // Test that getType method returns the correct data type
        data.add(1);
        Col col = new Col(label, data);
        assertEquals("test", col.getLabel());
    }

    @Test
    public void testGetElem() {
        // Test that get_elem method returns the correct element at a given index
        data.add(1);
        data.add(2);
        Col col = new Col(label, data);
        assertEquals(1, col.getElem(0));
        assertEquals(2, col.getElem(1));
    }

    @Test
    public void testAvg() {
        // Test that avg method returns the correct average of numeric elements
        data.add(1);
        data.add(2);
        Col col = new Col(label, data);
        assertEquals(1.5, col.avg(), 0.001);
    }

    @Test
    public void testInvalidAvg() {
        data.add(true);
        data.add(false);
        Col col = new Col(label, data);
        assertThrows(RuntimeException.class, col::avg);
    }

    @Test
    public void testMax() {
        // Test that max method returns the correct maximum of numeric elements
        data.add(1);
        data.add(2);
        Col col = new Col(label, data);
        assertEquals(2, col.max(), 0.001);
    }

    @Test
    public void testInvalidMax() {
        data.add(true);
        data.add(false);
        Col col = new Col(label, data);
        assertThrows(RuntimeException.class, col::max);
    }

    @Test
    public void testMin() {
        // Test that min method returns the correct minimum of numeric elements
        data.add(1);
        data.add(2);
        Col col = new Col(label, data);
        assertEquals(1, col.min(), 0.001);
    }

    @Test
    public void testInvalidMin() {
        data.add(true);
        data.add(false);
        Col col = new Col(label, data);
        assertThrows(RuntimeException.class, col::min);
    }

    @Test
    public void testTotal() {
        // Test that total method returns the correct sum of numeric elements
        data.add(1);
        data.add(2);
        Col col = new Col(label, data);
        assertEquals(3, col.total(), 0.001);
    }

    @Test
    public void testInvalidTotal() {
        data.add(true);
        data.add(false);
        Col col = new Col(label, data);
        assertThrows(RuntimeException.class, col::total);
    }
}
