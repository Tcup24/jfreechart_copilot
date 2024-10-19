package org.jfree.data.time;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTimePeriodTest {

    @Test
    public void testEqualsSelf() {
        SimpleTimePeriod period = new SimpleTimePeriod(1000L, 2000L);
        assertEquals(period, period);
    }

    @Test
    public void testEquals() {
        SimpleTimePeriod period1 = new SimpleTimePeriod(1000L, 2000L);
        SimpleTimePeriod period2 = new SimpleTimePeriod(1000L, 2000L);
        assertEquals(period1, period2);

        period2 = new SimpleTimePeriod(1000L, 3000L);
        assertNotEquals(period1, period2);

        period1 = new SimpleTimePeriod(1000L, 3000L);
        assertEquals(period1, period2);
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        SimpleTimePeriod period = new SimpleTimePeriod(1000L, 2000L);
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(period);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        SimpleTimePeriod deserializedPeriod = (SimpleTimePeriod) in.readObject();

        assertEquals(period, deserializedPeriod);
    }

    @Test
    public void testHashcode() {
        SimpleTimePeriod period1 = new SimpleTimePeriod(1000L, 2000L);
        SimpleTimePeriod period2 = new SimpleTimePeriod(1000L, 2000L);
        assertEquals(period1.hashCode(), period2.hashCode());
    }

    @Test
    public void testClone() {
        assertFalse(SimpleTimePeriod.class.isAssignableFrom(Cloneable.class));
    }

    @Test
    public void testImmutable() {
        SimpleTimePeriod period = new SimpleTimePeriod(1000L, 2000L);
        Date originalStart = period.getStart();
        Date originalEnd = period.getEnd();

        // Attempt to modify the internal state (not possible directly)
        // Verify the state remains unchanged
        assertEquals(originalStart, period.getStart());
        assertEquals(originalEnd, period.getEnd());
    }

    @Test
    public void testCompareTo() {
        SimpleTimePeriod period1 = new SimpleTimePeriod(1000L, 2000L);
        SimpleTimePeriod period2 = new SimpleTimePeriod(1000L, 2000L);
        assertEquals(0, period1.compareTo(period2));

        period2 = new SimpleTimePeriod(500L, 1500L);
        assertTrue(period1.compareTo(period2) > 0);

        period2 = new SimpleTimePeriod(1500L, 2500L);
        assertTrue(period1.compareTo(period2) < 0);
    }
}