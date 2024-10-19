package org.jfree.data.time;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimePeriodValuesTest {

    @Test
    public void testClone() throws CloneNotSupportedException {
        TimePeriodValues original = new TimePeriodValues("Test");
        TimePeriodValues clone = (TimePeriodValues) original.clone();

        assertNotSame(original, clone);
        assertEquals(original, clone);
    }

    @Test
    public void testAddValue() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2023), 100.0);

        assertEquals(100.0, series.getValue(0));
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        TimePeriodValues original = new TimePeriodValues("Test");
        original.add(new Year(2023), 100.0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        TimePeriodValues deserialized = (TimePeriodValues) ois.readObject();
        ois.close();

        assertEquals(original, deserialized);
    }

    @Test
    public void testEquals() {
        TimePeriodValues series1 = new TimePeriodValues("Test");
        TimePeriodValues series2 = new TimePeriodValues("Test");
        TimePeriodValues series3 = new TimePeriodValues("Different");

        assertEquals(series1, series2);
        assertNotEquals(series1, series3);
    }

    @Test
    public void test1161329() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2023), 100.0);
        series.delete(0, 0);
        series.add(new Year(2023), 200.0);

        assertEquals(200.0, series.getValue(0));
    }

    @Test
    public void testAdd() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2023), 100.0);

        assertEquals(100.0, series.getValue(0));

        assertThrows(IllegalArgumentException.class, () -> {
            series.add(null);
        });
    }

    @Test
    public void testGetMinStartIndex() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2022), 100.0);
        series.add(new Year(2023), 200.0);

        assertEquals(0, series.getMinStartIndex());
    }

    @Test
    public void testGetMaxStartIndex() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2022), 100.0);
        series.add(new Year(2023), 200.0);

        assertEquals(1, series.getMaxStartIndex());
    }

    @Test
    public void testGetMinMiddleIndex() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2022), 100.0);
        series.add(new Year(2023), 200.0);

        assertEquals(0, series.getMinMiddleIndex());
    }

    @Test
    public void testGetMaxMiddleIndex() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2022), 100.0);
        series.add(new Year(2023), 200.0);

        assertEquals(1, series.getMaxMiddleIndex());
    }

    @Test
    public void testGetMinEndIndex() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2022), 100.0);
        series.add(new Year(2023), 200.0);

        assertEquals(0, series.getMinEndIndex());
    }

    @Test
    public void testGetMaxEndIndex() {
        TimePeriodValues series = new TimePeriodValues("Test");
        series.add(new Year(2022), 100.0);
        series.add(new Year(2023), 200.0);

        assertEquals(1, series.getMaxEndIndex());
    }
}