package org.jfree.data.statistics;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class HistogramBinTest {

    @Test
    public void testEquals() {
        HistogramBin bin1 = new HistogramBin(0.0, 1.0);
        HistogramBin bin2 = new HistogramBin(0.0, 1.0);
        assertEquals(bin1, bin2);
    }

    @Test
    public void testCloning() throws CloneNotSupportedException {
        HistogramBin bin1 = new HistogramBin(0.0, 1.0);
        HistogramBin bin2 = (HistogramBin) bin1.clone();
        assertNotSame(bin1, bin2);
        assertEquals(bin1.getClass(), bin2.getClass());
        assertEquals(bin1, bin2);
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        HistogramBin bin1 = new HistogramBin(0.0, 1.0);

        // Serialize the object
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(bin1);
        out.flush();
        byte[] serializedData = byteOut.toByteArray();

        // Deserialize the object
        ByteArrayInputStream byteIn = new ByteArrayInputStream(serializedData);
        ObjectInputStream in = new ObjectInputStream(byteIn);
        HistogramBin bin2 = (HistogramBin) in.readObject();

        assertEquals(bin1, bin2);
    }
}