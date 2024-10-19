package org.jfree.data.xy;

import org.jfree.data.Range;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class RangeTest {

    @Test
    public void testConstructor() {
        Range range = new Range(1.0, 5.0);
        assertEquals(1.0, range.getLowerBound());
        assertEquals(5.0, range.getUpperBound());

        assertThrows(IllegalArgumentException.class, () -> {
            new Range(5.0, 1.0);
        });
    }

    @Test
    public void testEquals() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        Range range3 = new Range(2.0, 6.0);

        assertEquals(range1, range2);
        assertNotEquals(range1, range3);
        assertNotEquals(range1, null);
        assertNotEquals(range1, "Some String");
    }

    @Test
    public void testHashCode() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);

        assertEquals(range1.hashCode(), range2.hashCode());
    }

    @Test
    public void testContains() {
        Range range = new Range(1.0, 5.0);

        assertTrue(range.contains(3.0));
        assertFalse(range.contains(0.0));
        assertFalse(range.contains(6.0));
        assertFalse(range.contains(Double.NaN));
        assertFalse(range.contains(Double.POSITIVE_INFINITY));
        assertFalse(range.contains(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testConstrain() {
        Range range = new Range(1.0, 5.0);

        assertEquals(3.0, range.constrain(3.0));
        assertEquals(1.0, range.constrain(0.0));
        assertEquals(5.0, range.constrain(6.0));
        assertEquals(Double.NaN, range.constrain(Double.NaN));
    }

    @Test
    public void testIntersects() {
        Range range = new Range(1.0, 5.0);

        assertTrue(range.intersects(2.0, 4.0));
        assertTrue(range.intersects(0.0, 2.0));
        assertTrue(range.intersects(4.0, 6.0));
        assertFalse(range.intersects(5.1, 6.0));
        assertFalse(range.intersects(0.0, 0.9));
    }

    @Test
    public void testExpand() {
        Range range = new Range(1.0, 5.0);
        Range expanded = Range.expand(range, 0.5, 0.5);

        assertEquals(-1.0, expanded.getLowerBound());
        assertEquals(7.0, expanded.getUpperBound());

        assertThrows(IllegalArgumentException.class, () -> {
            Range.expand(null, 0.5, 0.5);
        });
    }

    @Test
    public void testShift() {
        Range range = new Range(1.0, 5.0);
        Range shifted = Range.shift(range, 2.0);

        assertEquals(3.0, shifted.getLowerBound());
        assertEquals(7.0, shifted.getUpperBound());

        assertThrows(IllegalArgumentException.class, () -> {
            Range.shift(null, 2.0);
        });
    }

    @Test
    public void testScale() {
        Range range = new Range(1.0, 5.0);
        Range scaled = Range.scale(range, 2.0);

        assertEquals(2.0, scaled.getLowerBound());
        assertEquals(10.0, scaled.getUpperBound());

        assertThrows(IllegalArgumentException.class, () -> {
            Range.scale(null, 2.0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Range.scale(range, -1.0);
        });
    }

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        Range original = new Range(1.0, 5.0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(original);
        oos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Range deserialized = (Range) ois.readObject();
        ois.close();

        assertEquals(original, deserialized);
    }

//    @Test
//    public void testCombineThree() {
//        Range range1 = new Range(1.0, 5.0);
//        Range range2 = new Range(2.0, 6.0);
//        Range combined = Range.combine(range1, range2);
//
//        assertEquals(1.0, combined.getLowerBound());
//        assertEquals(6.0, combined.getUpperBound());
//
//        Range combinedWithNaN = Range.combine(range1, new Range(Double.NaN, Double.NaN));
//        assertEquals(new Range(Double.NaN, Double.NaN), combinedWithNaN);
//
//        combinedWithNaN = Range.combine(new Range(Double.NaN, Double.NaN), range2);
//        assertEquals(new Range(Double.NaN, Double.NaN), combinedWithNaN);
//    }

//    @Test
//    public void testCombine() {
//        Range range1 = new Range(1.0, 5.0);
//        Range range2 = new Range(2.0, 6.0);
//        Range combined = Range.combine(range1, range2);
//
//        assertEquals(1.0, combined.getLowerBound());
//        assertEquals(6.0, combined.getUpperBound());
//
//        Range combinedWithNaN = Range.combine(range1, new Range(Double.NaN, Double.NaN));
//        assertEquals(new Range(Double.NaN, Double.NaN), combinedWithNaN);
//
//        combinedWithNaN = Range.combine(new Range(Double.NaN, Double.NaN), range2);
//        assertEquals(new Range(Double.NaN, Double.NaN), combinedWithNaN);
//    }

    @Test
    public void testCombineIgnoringNaN() {
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(Double.NaN, Double.NaN);
        Range combined = Range.combineIgnoringNaN(range1, range2);

        assertEquals(range1, combined);

        combined = Range.combineIgnoringNaN(range2, range1);
        assertEquals(range1, combined);

        combined = Range.combineIgnoringNaN(range2, range2);
        assertNull(combined);
    }

    @Test
    public void testIsNaNRange() {
        Range range1 = new Range(Double.NaN, Double.NaN);
        Range range2 = new Range(1.0, 5.0);

        assertTrue(range1.isNaNRange());
        assertFalse(range2.isNaNRange());
    }
}