package org.jfree.data.statistics;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHistogramBinTest {

    @Test
    public void testAccepts() {
        SimpleHistogramBin bin = new SimpleHistogramBin(1.0, 2.0, true, true);
        assertTrue(bin.accepts(1.0));
        assertTrue(bin.accepts(1.5));
        assertTrue(bin.accepts(2.0));
        assertFalse(bin.accepts(0.9));
        assertFalse(bin.accepts(2.1));
    }

    @Test
    public void testOverlapsWith() {
        SimpleHistogramBin bin1 = new SimpleHistogramBin(1.0, 2.0, true, true);
        SimpleHistogramBin bin2 = new SimpleHistogramBin(1.5, 2.5, true, true);
        SimpleHistogramBin bin3 = new SimpleHistogramBin(2.1, 3.0, true, true);

        assertTrue(bin1.overlapsWith(bin2));
        assertFalse(bin1.overlapsWith(bin3));
        assertTrue(bin2.overlapsWith(bin3));
    }

    @Test
    public void testEquals() {
        SimpleHistogramBin bin1 = new SimpleHistogramBin(1.0, 2.0, true, true);
        SimpleHistogramBin bin2 = new SimpleHistogramBin(1.0, 2.0, true, true);
        SimpleHistogramBin bin3 = new SimpleHistogramBin(1.0, 2.0, false, true);
        SimpleHistogramBin bin4 = new SimpleHistogramBin(1.0, 2.0, true, true);
        bin4.setItemCount(5);

        assertEquals(bin1, bin2);
        assertNotEquals(bin1, bin3);
        assertNotEquals(bin1, bin4);
    }

    @Test
    public void testCloning() throws CloneNotSupportedException {
        SimpleHistogramBin bin1 = new SimpleHistogramBin(1.0, 2.0, true, true);
        bin1.setItemCount(5);
        SimpleHistogramBin bin2 = (SimpleHistogramBin) bin1.clone();

        assertNotSame(bin1, bin2);
        assertEquals(bin1.getClass(), bin2.getClass());
        assertEquals(bin1, bin2);

        bin2.setItemCount(10);
        assertNotEquals(bin1, bin2);
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        SimpleHistogramBin bin1 = new SimpleHistogramBin(1.0, 2.0, true, true);
        bin1.setItemCount(5);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(bin1);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        SimpleHistogramBin bin2 = (SimpleHistogramBin) ois.readObject();
        ois.close();

        assertEquals(bin1, bin2);
    }
}