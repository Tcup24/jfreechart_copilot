package org.jfree.data.statistics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BoxAndWhiskerCalculatorTest {

    private static final double EPSILON = 0.000000001;

    @Test
    public void testCalculateBoxAndWhiskerStatistics() {
        // Verify that passing a null list to calculateBoxAndWhiskerStatistics throws an IllegalArgumentException.
        boolean pass = false;
        try {
            BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(null);
        } catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // Test the calculation of the Box-and-Whisker statistics for a list with a single value (1.1).
        List<Number> values = new ArrayList<>();
        values.add(1.1);
        BoxAndWhiskerItem item = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(values);
        assertEquals(1.1, item.getMean().doubleValue(), EPSILON);
        assertEquals(1.1, item.getMedian().doubleValue(), EPSILON);
        assertEquals(1.1, item.getQ1().doubleValue(), EPSILON);
        assertEquals(1.1, item.getQ3().doubleValue(), EPSILON);
    }

    @Test
    public void testCalculateQ1() {
        // Verify that passing a null list to calculateQ1 throws an IllegalArgumentException.
        boolean pass = false;
        try {
            BoxAndWhiskerCalculator.calculateQ1(null);
        } catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // For an empty list, ensure that the result of calculateQ1 is NaN.
        List<Double> values = new ArrayList<>();
        double q1 = BoxAndWhiskerCalculator.calculateQ1(values);
        assertTrue(Double.isNaN(q1));

        // For a list with one element (1.0), ensure that Q1 equals 1.0.
        values.add(1.0);
        q1 = BoxAndWhiskerCalculator.calculateQ1(values);
        assertEquals(1.0, q1, EPSILON);

        // For a list with two elements (1.0, 2.0), ensure that Q1 equals 1.0.
        values.add(2.0);
        q1 = BoxAndWhiskerCalculator.calculateQ1(values);
        assertEquals(1.0, q1, EPSILON);

        // For a list with three elements (1.0, 2.0, 3.0), ensure that Q1 equals 1.5.
        values.add(3.0);
        q1 = BoxAndWhiskerCalculator.calculateQ1(values);
        assertEquals(1.5, q1, EPSILON);

        // For a list with four elements (1.0, 2.0, 3.0, 4.0), ensure that Q1 equals 1.5.
        values.add(4.0);
        q1 = BoxAndWhiskerCalculator.calculateQ1(values);
        assertEquals(1.5, q1, EPSILON);
    }

    @Test
    public void testCalculateQ3() {
        // Verify that passing a null list to calculateQ3 throws an IllegalArgumentException.
        boolean pass = false;
        try {
            BoxAndWhiskerCalculator.calculateQ3(null);
        } catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // For an empty list, ensure that the result of calculateQ3 is NaN.
        List<Number> values = new ArrayList<>();
        double q3 = BoxAndWhiskerCalculator.calculateQ3(values);
        assertTrue(Double.isNaN(q3));

        // For a list with one element (1.0), ensure that Q3 equals 1.0.
        values.add(1.0);
        q3 = BoxAndWhiskerCalculator.calculateQ3(values);
        assertEquals(1.0, q3, EPSILON);

        // For a list with two elements (1.0, 2.0), ensure that Q3 equals 2.0.
        values.add(2.0);
        q3 = BoxAndWhiskerCalculator.calculateQ3(values);
        assertEquals(2.0, q3, EPSILON);

        // For a list with three elements (1.0, 2.0, 3.0), ensure that Q3 equals 2.5.
        values.add(3.0);
        q3 = BoxAndWhiskerCalculator.calculateQ3(values);
        assertEquals(2.5, q3, EPSILON);

        // For a list with four elements (1.0, 2.0, 3.0, 4.0), ensure that Q3 equals 3.5.
        values.add(4.0);
        q3 = BoxAndWhiskerCalculator.calculateQ3(values);
        assertEquals(3.5, q3, EPSILON);
    }

    @Test
    public void test1593149() {
        // Use a list with five elements: [1.0, 2.0, NaN, 3.0, 4.0].
        List<Double> list = new ArrayList<>(5);
        list.add(1.0);
        list.add(2.0);
        list.add(Double.NaN);
        list.add(3.0);
        list.add(4.0);
        BoxAndWhiskerItem item = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(list);
        assertEquals(1.0, item.getMinRegularValue().doubleValue(), EPSILON);
        assertEquals(4.0, item.getMaxRegularValue().doubleValue(), EPSILON);
    }
}